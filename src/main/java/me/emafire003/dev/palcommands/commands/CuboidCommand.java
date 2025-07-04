package me.emafire003.dev.palcommands.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.CuboidEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class CuboidCommand implements PALCommand {

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.getWorld().isClient()){
                return 0;
            }
            Vec3d pos = Vec3ArgumentType.getVec3(context, "origin");
            Vec3d target = Vec3ArgumentType.getVec3(context, "target");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            if(BoolArgumentType.getBool(context, "useCorners")){
                CuboidEffect effect = new CuboidEffect(source.getWorld(), particle, pos, target,
                        IntegerArgumentType.getInteger(context, "particlesPerRow"),
                        DoubleArgumentType.getDouble(context, "padding"),
                        BoolArgumentType.getBool(context, "blockSnap"));
                effect.setForced(BoolArgumentType.getBool(context, "force"));
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));
            }else{
                CuboidEffect effect = new CuboidEffect(source.getWorld(), particle, pos, IntegerArgumentType.getInteger(context, "particlesPerRow"),
                        target.getX(), target.getY(), target.getZ(), // the lengths of the cuboid
                        DoubleArgumentType.getDouble(context, "padding"),
                        BoolArgumentType.getBool(context, "blockSnap"));
                effect.setForced(BoolArgumentType.getBool(context, "force"));
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));
            }


            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }

    private int spawnDemo(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.getWorld().isClient()){
                return 0;
            }
            Vec3d pos = Vec3ArgumentType.getVec3(context, "origin");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");
            CuboidEffect effect = CuboidEffect.builder(source.getWorld(), particle, pos)
                    .xLength(1).yLength(1).zLength(1).build();
            effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }

    public LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess) {
        return CommandManager
                .literal("cuboid")
                .then(CommandManager.literal("demo")
                        .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                                .then(CommandManager.argument("origin", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                .executes(this::spawnDemo)
                                        )
                                )
                        )
                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("origin", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("useCorners", BoolArgumentType.bool())
                                        .then(CommandManager.argument("target", Vec3ArgumentType.vec3())
                                                .then(CommandManager.argument("particlesPerRow", IntegerArgumentType.integer(0))
                                                        .then(CommandManager.argument("padding", DoubleArgumentType.doubleArg())
                                                                .then(CommandManager.argument("blockSnap", BoolArgumentType.bool())
                                                                        .then(CommandManager.argument("force", BoolArgumentType.bool())
                                                                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                        .executes(this::spawnEffect)
                                                                                )
                                                                        )
                                                                )
                                                        )

                                                )
                                        )
                                )
                        )

                )
                .build();
    }
}

package me.emafire003.dev.palcommands.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.DonutEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class DonutCommand implements PALCommand {

    private int spawnEffectNoYP(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");


                DonutEffect effect = new DonutEffect(source.getWorld(), particle, pos,
                        source.getPlayer().getYaw(), source.getPlayer().getPitch(),
                        IntegerArgumentType.getInteger(context, "particlesPerCircle"),
                        IntegerArgumentType.getInteger(context, "circles"),
                        FloatArgumentType.getFloat(context, "radiusDonut"),
                        FloatArgumentType.getFloat(context, "radiusTube"),
                        Vec3ArgumentType.getVec3(context, "rotation"),
                        FloatArgumentType.getFloat(context, "radDonutIncrease"),
                        FloatArgumentType.getFloat(context, "radTubeIncrease"),
                        IntegerArgumentType.getInteger(context, "particleIncrease"),
                        IntegerArgumentType.getInteger(context, "particleIncreaseEvery"),
                        IntegerArgumentType.getInteger(context, "circlesIncrease"),
                        IntegerArgumentType.getInteger(context, "circlesIncreaseEvery")
                );
                effect.setForced(BoolArgumentType.getBool(context, "force"));
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            }else{
                source.sendError(Text.literal("This command must be executed by a player! Try to specify the Yaw and Pitch if you are running the command from the console!"));
                return 0;
            }

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }

    private int spawnEffectSimple(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");

                DonutEffect effect = DonutEffect.builder(source.getWorld(), particle, pos)
                        .circles(IntegerArgumentType.getInteger(context, "circles"))
                        .particlesCircle(IntegerArgumentType.getInteger(context, "particlesPerCircle"))
                        .radiusDonut(FloatArgumentType.getFloat(context, "radiusDonut"))
                        .radiusTube(FloatArgumentType.getFloat(context, "radiusTube"))
                        .rotation(Vec3ArgumentType.getVec3(context, "rotation"))
                        .build();
                effect.setForced(BoolArgumentType.getBool(context, "force"));
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            }else{
                source.sendError(Text.literal("This command must be executed by a player! Try to specify the Yaw and Pitch if you are running the command from the console!"));
                return 0;
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
            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");

                DonutEffect effect = DonutEffect.builder(source.getWorld(), particle, pos).build();
                effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

            }else{
                source.sendError(Text.literal("This command must be executed by a player! Try to specify the Yaw and Pitch if you are running the command from the console!"));
                return 0;
            }

            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }

    private int spawnEffectFull(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");

            DonutEffect effect = new DonutEffect(source.getWorld(), particle, pos,
                    FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"),
                    IntegerArgumentType.getInteger(context, "particlesPerCircle"),
                    IntegerArgumentType.getInteger(context, "circles"),
                    FloatArgumentType.getFloat(context, "radiusDonut"),
                    FloatArgumentType.getFloat(context, "radiusTube"),
                    Vec3ArgumentType.getVec3(context, "rotation"),
                    FloatArgumentType.getFloat(context, "radDonutIncrease"),
                    FloatArgumentType.getFloat(context, "radTubeIncrease"),
                    IntegerArgumentType.getInteger(context, "particleIncrease"),
                    IntegerArgumentType.getInteger(context, "particleIncreaseEvery"),
                    IntegerArgumentType.getInteger(context, "circlesIncrease"),
                    IntegerArgumentType.getInteger(context, "increaseCirclesEvery")
            );
            effect.setForced(BoolArgumentType.getBool(context, "force"));
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
                .literal("donut")
                .then(CommandManager.literal("demo")
                        .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                                .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                .executes(this::spawnDemo)
                                        )
                                )
                        )
                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("particlesPerCircle", IntegerArgumentType.integer())
                                        .then(CommandManager.argument("circles", IntegerArgumentType.integer())
                                                .then(CommandManager.argument("radiusDonut", FloatArgumentType.floatArg())
                                                        .then(CommandManager.argument("radiusTube", FloatArgumentType.floatArg())
                                                                //TODO see what's center integers
                                                                .then(CommandManager.argument("rotation", Vec3ArgumentType.vec3())
                                                                        .then(CommandManager.argument("radDonutIncrease", FloatArgumentType.floatArg())
                                                                                .then(CommandManager.argument("radTubeIncrease", FloatArgumentType.floatArg())
                                                                                        .then(CommandManager.argument("particleIncrease", IntegerArgumentType.integer(0))
                                                                                                .then(CommandManager.argument("particleIncreaseEvery", IntegerArgumentType.integer(1))
                                                                                                        .then(CommandManager.argument("circlesIncrease", IntegerArgumentType.integer(0))
                                                                                                                .then(CommandManager.argument("circlesIncreaseEvery", IntegerArgumentType.integer(1))
                                                                                                                        .then(CommandManager.argument("force", BoolArgumentType.bool())
                                                                                                                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                                                        .executes(this::spawnEffectNoYP)
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )

                                        )
                                )


                        )

                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("particlesPerCircle", IntegerArgumentType.integer())
                                        .then(CommandManager.argument("circles", IntegerArgumentType.integer())
                                                .then(CommandManager.argument("radiusDonut", FloatArgumentType.floatArg())
                                                        .then(CommandManager.argument("radiusTube", FloatArgumentType.floatArg())
                                                                .then(CommandManager.argument("rotation", Vec3ArgumentType.vec3())
                                                                        .then(CommandManager.argument("force", BoolArgumentType.bool())
                                                                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                        .executes(this::spawnEffectSimple)
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )

                                        )
                                )


                        )

                )
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("yaw", FloatArgumentType.floatArg())
                                        .then(CommandManager.argument("pitch", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("particlesPerCircle", IntegerArgumentType.integer())
                                                        .then(CommandManager.argument("circles", IntegerArgumentType.integer())
                                                                .then(CommandManager.argument("radiusDonut", FloatArgumentType.floatArg())
                                                                        .then(CommandManager.argument("radiusTube", FloatArgumentType.floatArg())
                                                                                .then(CommandManager.argument("rotation", Vec3ArgumentType.vec3())
                                                                                        .then(CommandManager.argument("radDonutIncrease", FloatArgumentType.floatArg())
                                                                                                .then(CommandManager.argument("radTubeIncrease", FloatArgumentType.floatArg())
                                                                                                        .then(CommandManager.argument("particleIncrease", IntegerArgumentType.integer(0))
                                                                                                                .then(CommandManager.argument("particleIncreaseEvery", IntegerArgumentType.integer(1))
                                                                                                                        .then(CommandManager.argument("circlesIncrease", IntegerArgumentType.integer(0))
                                                                                                                                .then(CommandManager.argument("circlesIncreaseEvery", IntegerArgumentType.integer(1))
                                                                                                                                        .then(CommandManager.argument("force", BoolArgumentType.bool())
                                                                                                                                                .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                                                                        .executes(this::spawnEffectFull)
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
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

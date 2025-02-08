package me.emafire003.dev.palcommands.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.particleanimationlib.effects.ImageEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ImageCommand implements PALCommand {

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{

            Vec3d vel = Vec3ArgumentType.getVec3(context, "angVelocity");
            ImageEffect imageEffect = ImageEffect
                    .builder(source.getWorld(), Vec3ArgumentType.getVec3(context, "pos"), StringArgumentType.getString(context, "filePath"))
                    .transparency(BoolArgumentType.getBool(context, "transparent"))
                    .scale(FloatArgumentType.getFloat(context, "scale"))
                    .orient(BoolArgumentType.getBool(context, "orientPlayer"))
                    .rotation(Vec3ArgumentType.getVec3(context, "rotation"))
                    .enableRotation(BoolArgumentType.getBool(context, "rotatesDynamically"))
                    .angularVelocityX(vel.x).angularVelocityY(vel.y).angularVelocityZ(vel.z)
                    .particleSize(FloatArgumentType.getFloat(context, "particleSize"))
                    .stepX(IntegerArgumentType.getInteger(context, "stepX"))
                    .stepX(IntegerArgumentType.getInteger(context, "stepY"))
                    .blackAndWhite(BoolArgumentType.getBool(context, "blackAndWhite"))
                    .invertColors(BoolArgumentType.getBool(context, "invertColors"))
                    .build();
            imageEffect.runFor(IntegerArgumentType.getInteger(context, "duration"));
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

            ImageEffect imageEffect = ImageEffect
                    .builder(source.getWorld(), Vec3ArgumentType.getVec3(context, "pos"), StringArgumentType.getString(context, "filePath"))
                    .transparency(true).build();
            imageEffect.runFor(IntegerArgumentType.getInteger(context, "duration"));
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            source.sendFeedback( () -> Text.literal("Error: " + e),false);
            return 0;
        }
    }


    public LiteralCommandNode<ServerCommandSource> getNode(CommandRegistryAccess registryAccess) {
        return CommandManager
                .literal("image")
                .then(CommandManager.literal("demo")
                        .then(CommandManager.argument("filePath", StringArgumentType.string())
                                .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                .executes(this::spawnDemo)
                                        )
                                )
                        )
                )
                .then(CommandManager.argument("filePath", StringArgumentType.string())
                        .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("transparent", BoolArgumentType.bool())
                                        .then(CommandManager.argument("scale", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("particleSize", FloatArgumentType.floatArg(0))
                                                        .then(CommandManager.argument("stepX", IntegerArgumentType.integer(1))
                                                                .then(CommandManager.argument("stepY", IntegerArgumentType.integer(1))
                                                                        .then(CommandManager.argument("orientPlayer", BoolArgumentType.bool())
                                                                                .then(CommandManager.argument("rotation", Vec3ArgumentType.vec3())
                                                                                        .then(CommandManager.argument("rotatesDynamically", BoolArgumentType.bool())
                                                                                                .then(CommandManager.argument("angVelocity", Vec3ArgumentType.vec3())
                                                                                                        .then(CommandManager.argument("blackAndWhite", BoolArgumentType.bool())
                                                                                                                .then(CommandManager.argument("invertColors", BoolArgumentType.bool())
                                                                                                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer(0))
                                                                                                                                .executes(this::spawnEffect)
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )

                                                                        ))
                                                        )
                                                )

                                        )
                                )

                        )

                )
                .build();
    }
}

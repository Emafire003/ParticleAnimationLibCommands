package me.emafire003.dev.palcommands.commands;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.palcommands.commands.arguments.FontStyleArgumentType;
import me.emafire003.dev.palcommands.commands.arguments.FontStyles;
import me.emafire003.dev.particleanimationlib.effects.TextEffect;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

//TODO when and if i introduce effect storing add the "realtime/updatable" thing too
public class TextCommand implements PALCommand {

    private int spawnEffectNoYP(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            if(source.isExecutedByPlayer() && source.getPlayer() != null){
                Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
                ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");

                //Determines the font
                String fontName = StringArgumentType.getString(context, "font_name");
                FontStyles fontStyle = FontStyleArgumentType.getStyle(context, "font_style");
                int fontSize = IntegerArgumentType.getInteger(context, "font_size");
                int fontType = Font.PLAIN;
                if(fontStyle.equals(FontStyles.BOLD)){
                    fontType = Font.BOLD;
                }else if(fontStyle.equals(FontStyles.ITALIC)){
                    fontType = Font.ITALIC;
                }
                Font font = new Font(fontName, fontType, fontSize);

                TextEffect effect = new TextEffect(source.getWorld(), particle, pos,
                        source.getPlayer().getYaw(), source.getPlayer().getPitch(),
                        StringArgumentType.getString(context, "text_to_display"),
                        BoolArgumentType.getBool(context, "invert"),
                        IntegerArgumentType.getInteger(context, "stepX"),
                        IntegerArgumentType.getInteger(context, "stepY"),
                        FloatArgumentType.getFloat(context, "size"),
                        false,
                        font
                );
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

    private int spawnEffect(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();

        try{
            Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");

            //Determines the font
            String fontName = StringArgumentType.getString(context, "font_name");
            FontStyles fontStyle = FontStyleArgumentType.getStyle(context, "font_style");
            int fontSize = IntegerArgumentType.getInteger(context, "font_size");
            int fontType = Font.PLAIN;
            if(fontStyle.equals(FontStyles.BOLD)){
                fontType = Font.BOLD;
            }else if(fontStyle.equals(FontStyles.ITALIC)){
                fontType = Font.ITALIC;
            }
            Font font = new Font(fontName, fontType, fontSize);

            TextEffect effect = new TextEffect(source.getWorld(), particle, pos,
                    FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"),
                    StringArgumentType.getString(context, "text_to_display"),
                    BoolArgumentType.getBool(context, "invert"),
                    IntegerArgumentType.getInteger(context, "stepX"),
                    IntegerArgumentType.getInteger(context, "stepY"),
                    FloatArgumentType.getFloat(context, "size"),
                    false,
                    font
            );
            effect.runFor(IntegerArgumentType.getInteger(context, "duration"));

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
            Vec3d pos = Vec3ArgumentType.getVec3(context, "originPos");
            ParticleEffect particle = ParticleEffectArgumentType.getParticle(context, "particle");

            TextEffect effect = TextEffect.builder(source.getWorld(), particle, pos)
                    .text("Hello world").size(0.1f).font(new Font("Arial", Font.PLAIN, 15)).build();
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
                .literal("text")
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
                                .then(CommandManager.argument("text_to_display", StringArgumentType.string())
                                        .then(CommandManager.argument("invert", BoolArgumentType.bool())
                                                .then(CommandManager.argument("stepX", IntegerArgumentType.integer(1))
                                                        .then(CommandManager.argument("stepY", IntegerArgumentType.integer(1))
                                                                .then(CommandManager.argument("size", FloatArgumentType.floatArg())
                                                                        .then(CommandManager.argument("font_name", StringArgumentType.string())
                                                                                .then(CommandManager.argument("font_style", FontStyleArgumentType.fontStyle())
                                                                                        .then(CommandManager.argument("font_size", IntegerArgumentType.integer(0))
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
                .then(CommandManager.argument("particle", ParticleEffectArgumentType.particleEffect(registryAccess))
                        .then(CommandManager.argument("originPos", Vec3ArgumentType.vec3())
                                .then(CommandManager.argument("yaw", FloatArgumentType.floatArg())
                                        .then(CommandManager.argument("pitch", FloatArgumentType.floatArg())
                                                .then(CommandManager.argument("text_to_display", StringArgumentType.string())
                                                        .then(CommandManager.argument("invert", BoolArgumentType.bool())
                                                                .then(CommandManager.argument("stepX", IntegerArgumentType.integer(1))
                                                                        .then(CommandManager.argument("stepY", IntegerArgumentType.integer(1))
                                                                                .then(CommandManager.argument("size", FloatArgumentType.floatArg())
                                                                                        .then(CommandManager.argument("font_name", StringArgumentType.string())
                                                                                                .then(CommandManager.argument("font_style", FontStyleArgumentType.fontStyle())
                                                                                                        .then(CommandManager.argument("font_size", IntegerArgumentType.integer(0))
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
                                        )
                                )

                        )

                )
                .build();
    }
}

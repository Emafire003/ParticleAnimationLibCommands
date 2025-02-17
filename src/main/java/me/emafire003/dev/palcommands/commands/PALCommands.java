package me.emafire003.dev.palcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.palcommands.ParticleAnimationLibCommands;
import me.emafire003.dev.palcommands.commands.arguments.FontStyleArgumentType;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class PALCommands {

    public static void registerArguments(){
        ArgumentTypeRegistry.registerArgumentType(Identifier.of(ParticleAnimationLibCommands.MOD_ID, "font_style"), FontStyleArgumentType.class, ConstantArgumentSerializer.of(FontStyleArgumentType::fontStyle));

    }

    //Based on Factions' code https://github.com/ickerio/factions
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralCommandNode<ServerCommandSource> pal_commands = CommandManager
                .literal("pal")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .build();

        dispatcher.getRoot().addChild(pal_commands);

        LiteralCommandNode<ServerCommandSource> pal_alias = CommandManager
                .literal("particleanimationlib")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .build();


        dispatcher.getRoot().addChild(pal_alias);

        PALCommand[] commands = new PALCommand[] {
                new AnimatedBallCommand(),
                new ArcCommand(),
                new CuboidCommand(),
                new SphereCommand(),
                new ConeCommand(),
                new VortexCommand(),
                new LineCommand(),
                new AnimatedCircleCommand(),
                new ImageCommand(),
                new TextCommand(),
                new FontListCommand(),
                new DonutCommand()
        };

        for (PALCommand command : commands) {
            pal_commands.addChild(command.getNode(registryAccess));
        }
    }
}

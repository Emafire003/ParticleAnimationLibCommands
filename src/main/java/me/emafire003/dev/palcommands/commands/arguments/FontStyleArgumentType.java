package me.emafire003.dev.palcommands.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Collection;

public class FontStyleArgumentType implements ArgumentType<FontStyles> {
    public static FontStyleArgumentType fontStyle() {
        return new FontStyleArgumentType();
    }

    public static <S> FontStyles getStyle(CommandContext<S> context, String name) {
        // Note that you should assume the CommandSource wrapped inside of the CommandContext will always be a generic type.
        // If you need to access the ServerCommandSource make sure you verify the source is a server command source before casting.
        return context.getArgument(name, FontStyles.class);
    }

    private static final Collection<String> EXAMPLES = Arrays.asList("plain", "bold", "italic");

    @Override
    public FontStyles parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor(); // The starting position of the cursor is at the beginning of the argument.
        if (!reader.canRead()) {
            reader.skip();
        }

        // Now we check the contents of the argument till either we hit the end of the command line (When canRead becomes false)
        // Otherwise we go till reach reach a space, which signifies the next argument
        while (reader.canRead() && reader.peek() != ' ') { // peek provides the character at the current cursor position.
            reader.skip(); // Tells the StringReader to move it's cursor to the next position.
        }

        // Now we substring the specific part we want to see using the starting cursor position and the ends where the next argument starts.
        String typeString = reader.getString().substring(argBeginning, reader.getCursor()).toUpperCase();
        try {
            // Now our actual logic.
            return FontStyles.valueOf(typeString); // And we return our type, in this case the parser will consider this argument to have parsed properly and then move on.
        } catch (Exception ex) {
            // UUIDs can throw an exception when made by a string, so we catch the exception and repackage it into a CommandSyntaxException type.
            // Create with context tells Brigadier to supply some context to tell the user where the command failed at.
            // Though normal create method could be used.
            throw new SimpleCommandExceptionType(Text.literal(ex.getMessage())).createWithContext(reader);
        }
    }

    @Override
    public Collection<String> getExamples() { // Brigadier has support to show examples for what the argument should look like, this should contain a Collection of only the argument this type will return. This is mainly used to calculate ambiguous commands which share the exact same
        return EXAMPLES;
    }
}

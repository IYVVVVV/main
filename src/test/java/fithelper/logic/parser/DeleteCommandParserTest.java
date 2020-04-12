package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_INDEX;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fithelper.commons.core.index.Index;
import fithelper.logic.commands.DeleteCommand;
import fithelper.model.entry.Type;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parseValidArgsReturnsDeleteCommand() {
        assertParseSuccess(parser,
                DeleteCommand.COMMAND_WORD + " " + PREFIX_TYPE + "f" + " " + PREFIX_INDEX + "1",
                new DeleteCommand(new Type("food"), new Index(0)));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}




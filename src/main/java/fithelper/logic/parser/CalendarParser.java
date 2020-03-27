package fithelper.logic.parser;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_MODE;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import fithelper.logic.commands.CalendarCommand;

/**
 * Parse the command for calendar
 */
public class CalendarParser {
    /**
     *  Returns calendarCommand after setting the date
     */
    private CalendarCommand calendarCommand(ArgumentMultimap argMultimap) {
        CalendarCommand calendarCommand = new CalendarCommand();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            calendarCommand.setDate(argMultimap.getValue(PREFIX_DATE).get());
        }
        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            calendarCommand.setMode(argMultimap.getValue(PREFIX_MODE).get());
        }
        return calendarCommand;
    }

    /**
     * Parse the args by prefix_date
     * @param args string containing user input
     * @return ArgumentMultimap with mapped parameters
     */
    public CalendarCommand parse(String args) {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_DATE, PREFIX_MODE);
        if (argMultimap.getValue(PREFIX_DATE).isPresent() && argMultimap.getValue(PREFIX_MODE).isPresent()) {
            return calendarCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            argMultimap.put(PREFIX_MODE, "tb");
            return calendarCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            argMultimap.put(PREFIX_DATE, LocalDateTime.now().toString());
            return calendarCommand(argMultimap);
        } else {
            argMultimap.put(PREFIX_DATE, LocalDateTime.now().toString());
            argMultimap.put(PREFIX_MODE, "tb");
            return calendarCommand(argMultimap);
        }
    }
}


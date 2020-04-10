package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_SEMESTER;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Year;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.FocusArea;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;

//@@author joycelynteo

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_SEMESTER = "Please enter the year in the form Y.S, where Y is the year "
            + "(positive integer less than 10) and S is the semester (1 or 2).";


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String formattedName = name.trim().toLowerCase();
        if (name.equals("")) {
            throw new ParseException(MESSAGE_MISSING_NAME);
        }
        if (!Name.isValidName(formattedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(formattedName);
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String formattedModuleCode = moduleCode.trim().toUpperCase();
        if (formattedModuleCode.equals("")) {
            throw new ParseException(MESSAGE_MISSING_MODULE);
        }
        if (!ModuleCode.isValidCode(formattedModuleCode)) {
            throw new ParseException(MESSAGE_INVALID_MODULE);
        }
        return new ModuleCode(formattedModuleCode);
    }

    /**
     * Checks that input is an integer
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a {@code String year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        String trimmedYear = year.trim();
        if (year.equals("")) {
            throw new ParseException(MESSAGE_MISSING_SEMESTER);
        }
        if (!Year.isValidCode(year)) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses a {@code String grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static String parseGrade(String grade) throws ParseException {
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return trimmedGrade;
    }

    /**
     * Parses a {@code String deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static String[] parseDeadline(String deadline) throws ParseException {
        String trimmedDeadline = deadline.trim();
        if (!trimmedDeadline.contains(" ")) {
            if (!Deadline.isValidDate(trimmedDeadline)) {
                throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
            }
            String[] datetime = {trimmedDeadline, "23:59"};
            return datetime;
        } else {
            String[] dateTime = trimmedDeadline.split(" ");
            if (dateTime.length < 2 || !Deadline.isValidDeadline(dateTime[0], dateTime[1])) {
                throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
            }
            return dateTime;
        }
    }

    /**
     * Parses a {@code String courseName} into a {@code CourseName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code courseName} is invalid.
     */
    public static CourseName parseCourseName(String courseName) throws ParseException {
        requireNonNull(courseName);
        String trimmedCourseName = courseName.trim().toUpperCase();
        if (trimmedCourseName.equals("")) {
            throw new ParseException(MESSAGE_MISSING_COURSE);
        }
        if (!CourseName.isValid(trimmedCourseName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COURSE, NewCommand.MESSAGE_USAGE));
        }
        return new CourseName(trimmedCourseName);
    }

    /**
     * Parses a {@code String focusArea}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code focusArea} is invalid.
     */
    public static FocusArea parseFocusArea(CourseName courseName, String focusArea) throws ParseException {
        requireNonNull(focusArea);
        String trimmedFocusArea = focusArea.trim().toUpperCase();
        if (trimmedFocusArea.equals("")) {
            throw new ParseException(MESSAGE_MISSING_COURSE_FOCUS_AREA);
        }
        if (!FocusArea.isValid(courseName, trimmedFocusArea)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COURSE_FOCUS_AREA, NewCommand.MESSAGE_USAGE));
        }
        return new FocusArea(trimmedFocusArea);
    }
}

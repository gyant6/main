package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.exceptions.DuplicatePersonException;

/**
 * Creates a new profile.
 */
public class NewCommand extends Command {

    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new profile. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COURSE_NAME + "COURSE "
            + PREFIX_CURRENT_SEMESTER + "CURRENT_SEMESTER" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_COURSE_NAME + "Computer Science "
            + PREFIX_CURRENT_SEMESTER + "4";

    public static final String MESSAGE_SUCCESS = "New profile created: %1$s";
    public static final String MESSAGE_DUPLICATE_PROFILE = "Error: Profile with name %1$s exists. "
            + "You can only have one profile.";

    private final Profile toAdd;

    public NewCommand(Profile profile) {
        requireNonNull(profile);
        toAdd = profile;
    }

    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) throws CommandException {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);

        try {
            profileManager.addPerson(toAdd);
        } catch (DuplicatePersonException e) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PROFILE, toAdd.getName()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NewCommand // instanceof handles nulls
                && toAdd.equals(((NewCommand) other).toAdd));
    }


}

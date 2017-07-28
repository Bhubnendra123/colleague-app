package views;

/**
 * Created by developer01 on 29/06/2017.
 */
public interface TransformationMethodCompat2 extends TransformationMethodCompat{
    /**
     * Relax the contract of TransformationMethod to allow length changes,
     * or revert to the length-restricted behavior.
     *
     * @param allowLengthChanges true to allow the transformation to change the length
     *                           of the input string.
     */
    public void setLengthChangesAllowed(boolean allowLengthChanges);
}

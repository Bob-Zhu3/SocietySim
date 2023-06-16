import java.util.Comparator;

public class NetWorthSorter implements Comparator<Person>
{

    public int compare(Person one, Person another)
    {
        int returnVal = 0;
        if (one.getNetWorth() < another.getNetWorth())
            returnVal = 1;
        else if (one.getNetWorth() > another.getNetWorth())
            returnVal = -1;
        else if (one.getNetWorth() == another.getNetWorth())
            returnVal =  0;
        return returnVal;
    }
}
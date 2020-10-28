// This Subset class is a wrapper that represents a subset.

import java.util.ArrayList;

public class Subset implements Comparable<Subset>{
    private ArrayList<Integer> numbers = new ArrayList<>();
    private int id = -1;

    public Subset(int id)
    {
        this.id = id;
    }

    public void setNumbers(ArrayList<Integer> numbers)
    {
        this.numbers = numbers;
    }

    public int getId()
    {
        return this.id;
    }

    public ArrayList<Integer> getNumbers()
    {
        return numbers;
    }

    public boolean contains(Element v)
    {
        return this.numbers.contains(v.getValue());
    }

    @Override
    public String toString()
    {
        String s = ", [";
        for (int i = 0;i<this.numbers.size();i++)
        {
            s+= this.numbers.get(i)+", ";
        }
        s = s.substring(0, s.length()-2);
        s += "]";
        return "id: "+id+s;
    }

    @Override
    public int compareTo(Subset s) {
        if (s.getNumbers().size()>this.numbers.size()) return 1;
        else if(s.getNumbers().size()<this.numbers.size()) return -1;
        else return 0;
    }

    // Check if this subset has the integer i
    public boolean hasValue(int i)
    {
        for (int j = 0; j<this.numbers.size();j++)
        {
            if (this.numbers.get(j)==i)
                return true;
        }
        return false;
    }

    // Check if the subset is already covered in the partial result
    public boolean isCovered (MinSet partial)
    {
        for (int i = 0; i<this.getNumbers().size();i++)
        {
            Integer e = this.getNumbers().get(i);
            if (!partial.getIsIncluded()[e-1]) return false;
        }
        return true;
    }

    public boolean isNoneCovered (MinSet partial)
    {
        int count = 0;
        for (int i = 0; i<this.getNumbers().size();i++)
        {
            Integer e = this.getNumbers().get(i);
            if (partial.getIsIncluded()[e-1]&&count++==1) return false;

        }
        return true;
    }

}

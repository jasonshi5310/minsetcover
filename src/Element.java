// This Element class is an wrapper that contains information for a Element in a set

import java.util.ArrayList;

public class Element implements Comparable<Element>
{
    private int value = -1; // value of int
    // Subsets that contains this Vertex
    private ArrayList<Subset> included = new ArrayList<>();

    public Element(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public ArrayList<Subset> getIncluded()
    {
        return this.included;
    }

    public void addIncluded(Subset s)
    {
        this.included.add(s);
    }

    @Override
    public int compareTo(Element v)
    {
        if (v.getIncluded().size()>this.included.size())
        {
            return -1;
        }
        else if (v.getIncluded().size()<this.included.size())
        {
            return 1;
        }
        else
            return 0;
    }

    @Override
    public String toString()
    {
        String s = "; [";
        for (int i = 0; i<this.included.size();i++)
        {
            s += this.included.get(i).getId() + ", ";
        }
        s = s.substring(0, s.length()-2);
        return "value: "+this.value+s+"]";
    }
}

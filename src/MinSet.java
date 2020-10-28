// This AllSets class contains all the Subsets.
// If all covered, we find the result.

import java.util.ArrayList;

public class MinSet implements Cloneable {
    ArrayList<Subset> sets = new ArrayList<>();
    boolean[] isIncluded;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MinSet cloned = (MinSet) super.clone();
        cloned.setSets((ArrayList<Subset>) cloned.getSets().clone());
        cloned.setIsIncluded((boolean[]) cloned.getIsIncluded().clone());
        return cloned;
    }

    public void setIsIncluded(boolean[] isIncluded) {
        this.isIncluded = isIncluded;
    }

    public boolean[] getIsIncluded() {
        return isIncluded;
    }

    public ArrayList<Subset> getSets()
    {
        return this.sets;
    }

    public void setSets(ArrayList<Subset> sets)
    {
        this.sets = sets;
    }

    public MinSet (int size)
    {
        this.isIncluded = new boolean[size];
    }

    public boolean isSolution()
    {
        for (int i = 0; i<this.isIncluded.length;i++)
        {
            if (this.isIncluded[i]==false) return false;
        }
        return true;
    }

    public void addSet(Subset s)
    {
        this.sets.add(s);
        for(int i = 0;i <s.getNumbers().size();i++)
        {
            if (!this.isIncluded[s.getNumbers().get(i)-1])
                this.isIncluded[s.getNumbers().get(i)-1] = true;
        }
    }

    @Override
    public String toString()
    {
        String s = "MSC: "+this.sets.size()+" sets, [";
        for (int i = 0;i<this.sets.size();i++)
        {
            s += this.sets.get(i)+"; ";
        }
        s = s.substring(0,s.length()-2);
        s += "]";
        return s;
    }
}

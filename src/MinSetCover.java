import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


// This class contains the main method that find the MSC
// I have the File path in the code. To test different files, you need to manually change the filePath
// variable in Line 15. Sorry for the inconvenience!  Thank you!
public class MinSetCover {
    static MinSet finalResult = null;

    public static void main(String[] args) {
        String filePath = "C:\\Stony Brook\\Documents\\Junior\\Fall_2019\\CSE 373\\hw4\\testFiles\\s-rg-245-50.txt";
        File file = new File(filePath);
        ArrayList<Element> elements = new ArrayList<>();
        ArrayList<Subset> subsets = new ArrayList<>();
        int size = -1;
        int count = -1;
        boolean[] isIncluded = null;
        try {
            Scanner stdin = new Scanner(file);
            int s = Integer.valueOf(stdin.nextLine());
            size = s;
            isIncluded = new boolean[size];
            for (int i = 0; i< size;i++)
            {
                Element e = new Element(i+1);
                elements.add(e);
            }
            count = Integer.valueOf(stdin.nextLine());
            for (int k=0; k<count;k++)
            {
                String line = stdin.nextLine();
                line.trim();
                if (line.equals(""))
                    continue;
                String[] strs = line.split(" ");
                ArrayList<Integer> nums = new ArrayList<>();
                Subset set = new Subset(k+1);
                if (strs.length==1 && isIncluded[Integer.valueOf(strs[0])-1])
                    continue;
                for (int j =0; j<strs.length;j++)
                {
                    Integer num = Integer.valueOf(strs[j]);
                    if (!isIncluded[num-1]) isIncluded[num-1] = true;
                    Element e = elements.get(num-1);
                    e.addIncluded(set);
                    nums.add(num);
                }
                set.setNumbers(nums);
                subsets.add(set);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Collections.sort(elements); // Making elements a priority queue
        Collections.sort(subsets); // Making subsets a priority queue
        MinSet result = new MinSet(size);
        for (int k = 0; k<elements.size();k++)
        {
            Collections.sort(elements.get(k).getIncluded());
        }
        while(elements.get(0).getIncluded().size()==1)
        {
            Subset single = elements.get(0).getIncluded().get(0);
            result.addSet(single);
            elements.remove(0);
            subsets.remove(single);
            for (int i = 0;i<elements.size();i++)
            {
                if (single.hasValue(elements.get(i).getValue()))
                {
                    elements.remove(i--);
                }
            }
        }
        try {
            if (size<200|size<=count) {
                findMinSetCoverByElement(elements, result);
            }
            else {
                fillMinSetCoverBySubset(subsets, result);
                // Remove elements already in the partial result
                for (int i = 0; i<result.getIsIncluded().length;i++ )
                {
                    if (result.getIsIncluded()[i])
                    {
                        for (int j =0; j< elements.size();j++)
                        {
                            if (elements.get(j).getValue()==i+1)
                            {
                                elements.remove(j);
                                break;
                            }
                        }
                    }
                }
                findMinSetCoverByElement(elements,result);
                removeRedundancy();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println();
        if (finalResult!= null)
            System.out.println(finalResult);
        else System.out.println("Can not find");
    }

    // If we have fewer subsets than elements, we fill the partial result with subsets that have no overlaps
    public static void fillMinSetCoverBySubset(ArrayList<Subset> subsets, MinSet partial){
        if (subsets.size()==0) return;
        for (int i = 0; i<subsets.size();i++)
            if (subsets.get(i).isNoneCovered(partial))
            {
                partial.addSet(subsets.get(i));
            }
    }

    public static void removeRedundancy() throws CloneNotSupportedException {
        if (finalResult == null) return;
        ArrayList<Subset> result = finalResult.getSets();
        Collections.sort(result);
        MinSet s = (MinSet) finalResult.clone();
        int i = s.getSets().size()-1;
        while (i>=0)
        {
            Subset current = s.getSets().get(i);
            MinSet reduced = reducedSet(s,i);
            if (current.isCovered(reduced))
            {
                s = reduced;
            }
            i--;
        }
        finalResult = s;
    }


    // Return a MinSet with a reduced set.
    public static MinSet reducedSet(MinSet s, int index)
    {
        MinSet result = new MinSet(s.getIsIncluded().length);
        for (int i = 0; i<s.getSets().size();i++)
        {
            if (i == index) continue;
            result.addSet(s.getSets().get(i));
        }
        return result;
    }


    // If we have fewer elements than subsets
    public static void findMinSetCoverByElement(ArrayList<Element> elements, MinSet partial) throws CloneNotSupportedException {
        if (addSolution(partial)) return;
        if (elements.size()==0) return;
        Element current = elements.get(0);
        ArrayList<Element> copyOfElements = (ArrayList<Element>) elements.clone();
        MinSet copyOfPartial = (MinSet) partial.clone();
        for (int i = 0; i<current.getIncluded().size();i++)
        {

            Subset s = current.getIncluded().get(i);
            copyOfPartial.addSet(s);// add a set
            // Then remove all the elements that this set has
            for (int j =0; j< copyOfElements.size();j++)
            {
                if (s.hasValue(copyOfElements.get(j).getValue()))
                {
                    copyOfElements.remove(j--);
                }
            }
            if (finalResult!=null&& finalResult.getSets().size()<copyOfPartial.getSets().size())
                return;
            findMinSetCoverByElement(copyOfElements, copyOfPartial);
            copyOfElements = (ArrayList<Element>) elements.clone();
            copyOfPartial = (MinSet) partial.clone();
        }
    }


    // check if the partial result is a solution and a better solution than the final result
    // we have so far. if so, replace the final result with the partial result and return true;
    // else, return false.
    public static boolean addSolution(MinSet partial)
    {
        if (partial!=null && partial.isSolution())
        {
            //System.out.println(partial);
            if (finalResult==null)
            {
                finalResult = partial;
                //System.out.println("final result: "+finalResult);
                return true;
            }
            else if(finalResult.getSets().size() > partial.getSets().size()) {
                finalResult = partial;
                //System.out.println("final result: "+finalResult);
                return true;
            }
        }
        if (finalResult!= null && partial.getSets().size() >= finalResult.getSets().size())
            return true;
        return false;
    }

}

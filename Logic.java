import java.util.ArrayList;
public class Logic{
    static int count = 65;//the letter a, this is to make give pc nice leters
    int id;
    Logic i1,i2;
    public Logic(){id = count++;}
    public static void main(String[] args){
        Tester();
        PC a = new PC(); 
        PC b = new PC(); 
        PC c = new PC(); 
        PC d = new PC(); 

        Logic o1 = new Imp(new And(new Or(a,b),c),new Not(d));
        // Logic o1 = new Imp(new And(new Imp(a,b),new Imp(b,c)),new Imp(a,c));
        // Logic o2 = new Imp(a,b);
        // Logic o1 = new Imp(new Not(new Imp(new Or(a,b),c)),new And(a,c));      ¬((a ∨ b) ⊃ c) ⊃ (a ∧ c))

        System.out.println(o1); 
        // o1 = to_IND(o1);
        // System.out.println(o1); 
        // System.out.println(CNF(o1));
        System.out.println(CNF3(o1)); 
        //this currently works great, I just now need a system to simplify down the CNF
        // System.out.println(to_IND(o2)); 
    }
    public String toString(){
        return String.valueOf((char)id);
    }
    public Logic newSelf(Logic l1, Logic l2){return new Logic();};

    public static void Tester(){
        int pass = 0; int fail =0;
        Logic l = new Logic();
        Logic l2 = new Logic();
        if (l2.id == --Logic.count)pass++;else fail++;
        if (l.id == --Logic.count)pass++;else fail++;
        if (l.i1 == null && l.i2 == null)pass++;else fail++;
        if (l2.i1 == null && l2.i2 == null)pass++;else fail++;

        System.out.println("Logic class, passed : "+pass + ", failed:" + fail + ".");
        pass = 0; fail =0;

        PC a = new PC();
        PC b = new PC();
        PC c = new PC();
        if (a.toString().equals("A"))pass++;else fail++;
        if (b.toString().equals("B"))pass++;else fail++;
        if (c.toString().equals("C"))pass++;else fail++;
        if (a instanceof PC)pass++;else fail++;
        Logic.count = 65;
        System.out.println("Propositional Constant, passed : "+pass + ", failed:" + fail + ".");
        pass = 0; fail =0;

        Not n1 = new Not(a);
        if (n1.toString().equals("~A"))pass++;else fail++;
        And a1 = new And(a,b);
        if (a1.toString().equals("(A and B)"))pass++;else fail++;
        Or o1 = new Or(a,b);
        if (o1.toString().equals("(A or B)"))pass++;else fail++;
        Imp i1 = new Imp(a,b);
        if (i1.toString().equals("(A -> B)"))pass++;else fail++;
        Imp im1 = new Imp(a,new And(b,c));
        if (im1.toString().equals("(A -> (B and C))"))pass++;else fail++;
        System.out.println("Operators, passed : "+pass + ", failed:" + fail + ".");
        pass = 0; fail =0;
        Logic.count = 65;
        //example from wikipedia
        PC a2 = new PC(); 
        PC b2 = new PC(); 
        PC c2 = new PC(); 
        PC d2 = new PC(); 

        Logic o3 = new Imp(new And(new Or(a2,b2),c2),new Not(d2));
        if (CNF3(o3).toString().equals("[I, (I <-> (J -> K)), (J <-> (N and O)), (N <-> (R or S)), (K <-> ~D)]"))pass++;else fail++;
        Logic.count = 65;
        System.out.println("Forms, passed : "+pass + ", failed:" + fail + ".");
    }

    public static ArrayList<Logic> CNF3(Logic root){
        ArrayList<Logic> rtn = new ArrayList<Logic>();
        PC x1 = new PC();
        rtn.add(x1);
        CNF3R(root, rtn, x1);
        return rtn;
    }
    public static void CNF3R(Logic root, ArrayList<Logic> rtn, PC thing){
        if (root instanceof Not && root.i1 instanceof PC){
            rtn.add(new Equiv(thing,root));
        }else if (!(root instanceof PC)){
            PC x1 = new PC();
            PC x2 = new PC();
            rtn.add(new Equiv(thing,root.newSelf(x1,x2)));
            CNF3R(root.i1,rtn,x1);
            CNF3R(root.i2,rtn,x2);
        }
    }

    public static ArrayList<Logic> CNF(Logic root){
        ArrayList<Logic> rtn = new ArrayList<Logic>();
        extract(root,rtn);
        // simplify(rtn);
        return rtn;
    } 
    public static void extract(Logic root, ArrayList<Logic> rtn){
        if (root instanceof And){
            extract(root.i1,rtn);
            extract(root.i2,rtn);
        }else{
            rtn.add(root);
        }
    }
    public static ArrayList<Logic> simplify(ArrayList<Logic> rtn){
        for (Logic i: rtn){
            //now we search through.... and find duplicates or stuff that cancels 
        }
        return null;
    }

    public static Logic to_IND(Logic root){
        if (root instanceof And){
            root.i1 = Logic.to_IND(root.i1);
            root.i2 = Logic.to_IND(root.i2);
        }
        if (root instanceof Not && root.i1 instanceof Not){
            root = root.i1.i1;
        }else if (root instanceof Not){
            root.i1 = Logic.to_IND(root.i1);
        }
        if (root instanceof Imp){
            root = new Or(new Not(root.i1), root.i2);
            root.i1 = Logic.to_IND(root.i1);
            root.i2 = Logic.to_IND(root.i2);
        }
        if (root instanceof Equiv){
            root = new And(new Imp(root.i1,root.i2),new Imp(root.i2,root.i1));
            root.i1 = Logic.to_IND(root.i1);
            root.i2 = Logic.to_IND(root.i2);
        }
        if (root instanceof Not && root.i1 instanceof Or){//demorgan's law
            root = new And(new Not(root.i1.i1), new Not(root.i1.i2));
            root.i1 = Logic.to_IND(root.i1);
            root.i2 = Logic.to_IND(root.i2);
        }
        if (root instanceof Not && root.i1 instanceof And){//demorgan's law
            root = new Or(new Not(root.i1.i1), new Not(root.i1.i2));
            root.i1 = Logic.to_IND(root.i1);
            root.i2 = Logic.to_IND(root.i2);
        }
        if (root instanceof Or && root.i1 instanceof And){
            root = new And(new Or(root.i1.i1, root.i2),new Or(root.i1.i2, root.i2));
            root.i1 = Logic.to_IND(root.i1);
            root.i2 = Logic.to_IND(root.i2);
        }
        if (root instanceof Or && root.i2 instanceof And){
            root = new And(new Or(root.i2.i1, root.i1),new Or(root.i2.i2, root.i1));
            root.i1 = Logic.to_IND(root.i1);
            root.i2 = Logic.to_IND(root.i2);
        }
        return root;
    }
    // public Boolean eval(Boolean s){return s;}
}
class PC extends Logic{
    public PC(){super();}
    public PC newSelf(){return new PC();};
    public String toString(){
        return super.toString();
    }
}

class Not extends Logic{
    public Not(Logic lg){
        super();
        i1 = lg;
    }
    public Not newSelf(Logic lg){return new Not(lg);};
    public String toString(){
        return "~" + i1;
    }
    // public Boolean eval(Boolean s){return !s;}
}

class Or extends Logic{
    public Or(Logic l1, Logic l2){
        super();
        i1 = l1;
        i2 = l2;
    }
    public Or newSelf(Logic l1, Logic l2){return new Or(l1,l2);};
    public String toString(){
        return "(" + i1 + " or " + i2 + ")";
    }
    // public Boolean eval(Boolean s1, Boolean s2){return i1.eval(s1) || i2.eval(s2);}
}
class And extends Logic{
    public And(Logic l1, Logic l2){
        super();
        i1 = l1;
        i2 = l2;
    }
    public And newSelf(Logic l1, Logic l2){return new And(l1,l2);};
    public String toString(){
        return "(" + i1 + " and " + i2 + ")";
    }
    // public Boolean eval(Boolean s1, Boolean s2){return i1.eval(s1) && i2.eval(s2);}
}

class Imp extends Logic{
    public Imp(Logic l1, Logic l2){
        super();
        i1 = l1;
        i2 = l2;    
    }
    public Imp newSelf(Logic l1, Logic l2){return new Imp(l1,l2);};
    public String toString(){
        return "(" + i1 + " -> " + i2 + ")";
    }
    // public Boolean eval(Boolean s1, Boolean s2){return !i1.eval(s1) || i2.eval(s2);}
}
class Equiv extends Logic{
    public Equiv(Logic l1, Logic l2){
        super();
        i1 = l1;
        i2 = l2;    
    }
    public Equiv newSelf(Logic l1, Logic l2){return new Equiv(l1,l2);};
    public String toString(){
        return "(" + i1 + " <-> " + i2 + ")";
    }
    // public Boolean eval(Boolean s1, Boolean s2){return !i1.eval(s1) || i2.eval(s2);}
}
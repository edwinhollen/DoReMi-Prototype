package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/23/15
 */
public enum Chromatic {
    c_natural,
    c_sharp,
    d_natural,
    e_flat,
    e_natural,
    f_natural,
    f_sharp,
    g_natural,
    a_flat,
    a_natural,
    b_flat,
    b_natural;


    public static Integer indexOf(Chromatic needle){
        int i = 0;
        for(Chromatic c : Chromatic.values()){
            if(needle.equals(c)) return i;
        }
        return null;
    }
}

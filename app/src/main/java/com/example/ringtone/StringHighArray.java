/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ringtone;

import android.util.Log;

/**
 *
 * @author gemy
 */
public class StringHighArray {
    
    private String[] StringArray;
    private int nElems;
    int index;
    
    public StringHighArray(int max)
    {
        StringArray = new String[max];
        nElems = 0;
        index = 0;
    }
    
    public boolean find(String SearchKey)
    {
        for(index = 0;index<nElems;index++)
            if(StringArray[index] == SearchKey)
            {
                break;
            }
            
        if(index == nElems)
            return  false;
        else
            return true;
    }

    public String getDataFromArray(int index)
    {
        String output = null;
        if(index > nElems)
        {
            output += "position is over than size";
        }
        else {
            output += StringArray[index];
        }
        return output;
    }

    public int getSize()
    {
        return nElems;
    }

    public String[] getStringArray() {
        return StringArray;
    }

    public void setStringArray(String[] stringArray) {
        StringArray = stringArray;
    }

    public void setnElems(int nElems) {
        this.nElems = nElems;
    }

    public void insert(String value)
    {
        StringArray[nElems] = value;
        nElems++;
    }

    public String incNelms()
    {
        return StringArray[nElems++];
    }

    public String decNelms()
    {
        return StringArray[nElems--];
    }

    public void insert(String value,int index)
    {
        StringArray[index] = value;
        nElems++;
    }
    
    public boolean delete(String value)
    {
        for(index = 0;index<nElems;index++)
            if(value == StringArray[index])
                break;
        if(index == nElems)
        return false;
        else
        {
            for(int k = index;k<nElems;k++)
                StringArray[k] = StringArray[k+1];
            nElems--;
            return true;
        }
    }

    public void display()
    {
        for(int j = 0 ; j < nElems ; j++)
        {
            Log.d("the elementtttttttttt",StringArray[j] + " ");
            System.out.println("");
        }
    }

    
}

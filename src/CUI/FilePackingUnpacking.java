/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Required  Package
//
////////////////////////////////////////////////////////////////////////////////////////////

import java.io.IOException;
import java.util.Scanner;

import FilePackingUnpacking.PackingActivity;
import FilePackingUnpacking.UnpackingActivity;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Class  Name      :   FilePackingUnpacking
//  Description      :   Handles file packing and Unpacking Activity
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   14 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

public class FilePackingUnpacking 
{
    public static void main(String A[]) throws IOException
    {
        Scanner sobj = new Scanner(System.in);

        String FolderName = null;
        String PackFileName = null;

        System.out.println("Enter the Directory name :-");
        FolderName = sobj.nextLine();

        System.out.println("Enter the Pack File Name :- ");
        PackFileName = sobj.nextLine();

        PackingActivity pobj = new PackingActivity(FolderName, PackFileName);
        if(pobj.CheckDirectory() == -1)
        {
            System.out.println("There is no such Directory\n");
        }
        if(pobj.MakePackFile() == -1)
        {
            System.out.println(("There are no files in directory\n"));
        }
        else
        {
            System.out.println("File Packed Succesfully ");
        }
        
        UnpackingActivity unobj = new UnpackingActivity(PackFileName);

        if(unobj.UnpackFile() == -1)
        {
            System.out.println(("ther is no such Packed File\n"));
        }
        else
        {
            System.out.println("File is Unpacked Successfully ");
        }
    }
    
}

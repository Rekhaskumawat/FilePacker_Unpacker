//////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  Project        :   File Packer & Unpacker
//  Package        :   FilePacking
//  Class          :   PackingActivity
//  Author         :   Rekha Shankarlal Kumawat
//  Created On     :   11 February 2026
//
//  Description    :
//                   This class is responsible for packing all regular files from a specified
//                   directory into a single packed file. Each file is stored with:
//
//                   1. A fixed 100-byte header containing:
//                      - File name
//                      - File size
//
//                   2. Encrypted file content using XOR encryption (key = 0x11)
//
//                   This class is part of the custom user-defined package "FilePacking".
//
///////////////////////////////////////////////////////////////////////////////////////////////////////


package FilePackingUnpacking;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Required Inbuilt Package
//
////////////////////////////////////////////////////////////////////////////////////////////

import java.io.*;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Class Name  :   PackingActivity
//  Description :   Used to Pack file into one file
//  Author      :   Rekha Shankarlal Kumawat
//  Date        :   11 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

public class PackingActivity
{   
    // Input directory and output pack file name

    String Directory ;
    String PackFile ;
    String Header ;

    // File objects

    File dirfobj ;
    File Packfobj ;
    File fArr[];

    // File objects

    FileInputStream fiobj ;
    FileOutputStream foobj ;

    byte bHeader[] = new byte[100];                // Fixed-size header buffer
    byte Buffer[] = new byte[1024];                // Data buffer

    byte key = 0x11;                              // Encryption key

    boolean bRret = false ;

    int i = 0;
    int j = 0;
    int iRet = 0;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Function Name    :   PackingActivity
//  Description      :   Use to Intialise the members and allocate memory to resources
//  Input            :   String ,String
//  Output           :   Nothing
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   11 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

    public PackingActivity(String FolderName , String PackFileName)throws IOException
    {
        this.Directory = FolderName;
        this.PackFile = PackFileName;

    
        dirfobj = new File(this.Directory);
        Packfobj = new File(this.PackFile);

        foobj = new FileOutputStream(Packfobj);
    
        
    }//End of PackingAcitvity Constructor

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Function Name    :   CheckDirectory
//  Description      :   Validates whether the provided directory exists and is valid.
//  Input            :   Nothing
//  Output           :   return -1 if directory does not exist
//                       return 0 if directory  exist
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   11 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

    public int CheckDirectory()
    {
        bRret = (dirfobj.exists()) && (dirfobj.isDirectory());

        if(bRret  == false)
        {
            return -1;
        }
        return 0 ;

    } //End of CheckDirectory function

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Function Name    :   MakePackFile
//  Description      :   used to make a pack file of directory file
//  Input            :   Nothing
//  Output           :   return -1 if directory is empty
//                       return 0 on Succesfull
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   11 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

    public int MakePackFile()throws IOException
    {
        Packfobj.createNewFile();

        fArr = dirfobj.listFiles();

        if(fArr == null || fArr.length == 0)                // Check if directory is empty
        {
            return -1;
        }

        for(i =0 ; i < fArr.length ; i++)
        {
            if(fArr[i].isFile())                             // Process only regular files

            {
                fiobj = new FileInputStream(fArr[i]);

                /*--------------------------------------------------------
                     Header formation 
                  --------------------------------------------------------
                */

                Header = fArr[i].getName() + " " + fArr[i].length();

                for(j = Header.length() ; j <100 ; j++)
                {
                    Header = Header + " ";
                }
                bHeader = Header.getBytes();

                
                foobj.write(bHeader , 0 ,100);      // write header into packed file
                
                /*----------------------------------------------------------
                    FILE DATA PROCESSING
                  ----------------------------------------------------------
                */
                while ((iRet = fiobj.read(Buffer)) != -1) 
                {
                    for(j = 0 ; j < iRet ; j++ )              // Encrypt using XOR
                    {
                        Buffer[j] = (byte)( Buffer[j] ^ key) ;
                    }

                    
                    foobj.write(Buffer , 0 ,iRet);       // Write encrypted data
                }
                fiobj.close();
            }
            
        }
        foobj.close();
    
        return 0 ;

    }//End of MakePackFile Function

}// End of PackingActivity class


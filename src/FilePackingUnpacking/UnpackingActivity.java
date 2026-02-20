/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Project        :   File Packer & Unpacker
//  Package        :   FilePacking
//  Class          :   UnpackingActivity
//  Author         :   Rekha Shankarlal Kumawat
//  Created On     :   11 February 2026
//
//  Description    :
//                   This class is responsible for unpacking files from a packed file.
//
//                   Each file inside the packed file contains:
//                   1. A fixed 100-byte header:
//                          - File name
//                          - File size
//
//                   2. Encrypted file content (XOR encryption with key 0x11)
//
//                  This class reads the header, extracts metadata, decrypts the content,
//                  and recreates the original files.
//
/////////////////////////////////////////////////////////////////////////////////////////////

package FilePackingUnpacking;


/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Required Inbuilt Package
//
////////////////////////////////////////////////////////////////////////////////////////////

import java.io.*;

import javax.xml.crypto.Data;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Class Name  :   UnpackingActivity
//  Description :   Used to Unpack the Packed File
//  Author      :   Rekha Shankarlal Kumawat
//  Date        :   14 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

public class UnpackingActivity
{
    // Packed file name

    String PackFileName = null;

    File filefobj = null;                     // Packed file object
    File fobj = null;                         // Extracted file object
   
    // Stream objects
    
    FileInputStream fiobj = null;
    FileOutputStream foobj = null;

    boolean bRet = false;

    // Buffers
    byte bHeader[] = new byte[100];           // Header buffer (fixed 100 bytes)
    byte Buffer[] = new byte[100];            // Data buffer (dynamic size later)

    byte key = 0x11;                           // Encryption/Decryption key

    String Header = null;
    String Tokens[] = null;

    int iRet = 0;
    int FileSize = 0;
    int i = 0;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Function Name    :   UnpackingActivity
//  Description      :   Use to Intialise the members and allocate memory to resources
//  Input            :   String 
//  Output           :   Nothing
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   14 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

    public UnpackingActivity(String FileName)throws IOException
    {
        this.PackFileName = FileName;

        filefobj = new File(this.PackFileName);

        fiobj = new FileInputStream(filefobj);
       
    }// End of UnpackingActivity Constructor

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Function Name    :   CheckDirectory
//  Description      :   Extracts all files from packed file.
//  Input            :   Nothing
//  Output           :   return -1 if Packed file does not exist
//                       return 0 if directory  exist
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   14 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

    public int UnpackFile()throws IOException
    {
        bRet = filefobj.exists();                           // Check if packed file exists

        if(bRet == false)
        {
            return -1 ;
        }

        // Read until end of packed file

        while((iRet = fiobj.read(bHeader, 0, 100)) != -1) 
        {
            /*--------------------------------------------------------
                  Header Processing
              --------------------------------------------------------
            */
            Header = new String(bHeader);

            Header = Header.trim();
            Tokens = Header.split(" ");

            // Tokens[0] = File Name
            // Tokens[1] = File Size

            fobj = new File(Tokens[0]);

            fobj.createNewFile();
            foobj = new FileOutputStream(fobj);

            FileSize =Integer.parseInt(Tokens[1]);

            /*--------------------------------------------------------
                  Data Reading
              --------------------------------------------------------
            */

            Buffer = new byte[FileSize];

            // Read encrypted data from packed file

            fiobj.read(Buffer, 0, FileSize);

            /*--------------------------------------------------------
                  Decryotion Using XOR
              --------------------------------------------------------
            */

            for(i = 0 ; i <FileSize ; i++)
            {
                Buffer[i] = (byte)(Buffer[i] ^ key);
            }

            /*--------------------------------------------------------
                  Write Origianl Data
              --------------------------------------------------------
            */
            foobj.write(Buffer , 0 ,FileSize);

        }
        fiobj.close();
        return 0 ;
    
    }// End of Unpacking Function

}// End of UnpackingActivity class




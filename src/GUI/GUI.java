/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Project        :   File Packer & Unpacker (GUI Version)
//  Class          :   PackerUnpackerGUI
//  Author         :   Rekha Shankarlal Kumawat
//  Created On     :   11 February 2026
//
//  Description    :
//                  This class provides a graphical user interface (GUI) for
//                  packing and unpacking files.
//
//                  Features:
//                  -> Accept directory name
//                  -> Accept packed file name
//                  -> Pack files into single encrypted file
//                  -> Unpack encrypted file back to original files
//                  -> Button control workflow (Unpack disabled initially)
//
/////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Required  Package
//
////////////////////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import FilePackingUnpacking.*;

import java.net.*;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Class Name  :   PackerUnpackerGUI
//  Description :   his class builds the GUI and handles all button events
//  Author      :   Rekha Shankarlal Kumawat
//  Date        :   14 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

class PackerUnpackerGUI implements ActionListener
{
    // Main Frame
    JFrame fobj ;

    // Panel
    JPanel pobj1;

    // Buttons
    JButton btnPack ;
    JButton btnUnpack;

    // Text Fields
    JTextField txtDirectory;
    JTextField txtPackName ;

    // Labels
    JLabel Directory ;
    JLabel PackName ;
    JLabel ResultLable;

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Function Name    :   PackerUnpackerGUI
//  Description      :   Builds complete GUI layout and initializes components.
//  Input            :   Nothing
//  Output           :   Nothing
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   14 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

    public PackerUnpackerGUI()
    {
        fobj = new JFrame(" File_Packer_Unpacker ");

        /*--------------------------------------------------------
                  Labels
          --------------------------------------------------------
        */
        Directory = new JLabel(" Directory Name ");
        Directory.setBounds(30, 20, 130, 30);
        Directory.setFont(new Font("Segoe UI" ,Font.BOLD , 14));
        Directory.setForeground(new Color(44,62,80));

        PackName = new JLabel(" Packed File Name ");
        PackName.setBounds(30, 80, 130, 30);
        PackName.setFont(new Font("Segoe UI" ,Font.BOLD , 14));
        PackName.setForeground(new Color(44,62,80));

        /*--------------------------------------------------------
                  Text Fields
          --------------------------------------------------------
        */
        txtDirectory = new JTextField();
        txtDirectory.setBounds(180, 20, 150, 30);
        txtDirectory.setFont(new Font("Segoe UI", Font.PLAIN,14));

        txtPackName = new JTextField();
        txtPackName.setBounds(180 , 80 , 150 , 30);
        txtPackName.setFont(new Font("Segoe UI", Font.PLAIN,14));

        /*--------------------------------------------------------
                  Buttons
          --------------------------------------------------------
        */
        btnPack = new JButton(" Pack Files ");
        btnPack.setBounds(60, 140, 200, 30);
        btnPack.setFont(new Font ("Segoe UI" ,Font.BOLD, 15));
        btnPack.setBackground(new Color(41 , 128 ,185));
        btnPack.setForeground(Color.WHITE);

        btnUnpack = new JButton(" Unpack Files ");
        btnUnpack.setBounds(60, 190, 200, 30);
        btnUnpack.setFont(new Font ("Segoe UI" ,Font.BOLD, 15));
        btnUnpack.setBackground(new Color(39, 174 ,96));
        btnUnpack.setForeground(Color.WHITE);
        
        // Initaially disable Unpack Button
        btnUnpack.setEnabled(false);                                        

        /*--------------------------------------------------------
                  Result Lable
          --------------------------------------------------------
        */
        ResultLable = new JLabel();
        ResultLable.setBounds(30, 240, 250, 30);
        ResultLable.setFont(new Font("Segoe UI" ,Font.PLAIN , 14));
        ResultLable.setForeground(new Color(44,62,80));

        /*--------------------------------------------------------
                  Panel
          --------------------------------------------------------
        */
        pobj1 = new JPanel();
        pobj1.setLayout(null);
        pobj1.setBounds(10 , 10, 365, 340);
        pobj1.setBackground(new Color(245 , 247 , 250));

        pobj1.add(Directory);
        pobj1.add(PackName);
        pobj1.add(txtPackName);
        pobj1.add(txtDirectory);
        pobj1.add(btnPack);
        pobj1.add(btnUnpack);
        pobj1.add(ResultLable);

        fobj.add(pobj1);

        // Register action listeners
        btnPack.addActionListener(this);
        btnUnpack.addActionListener(this);

        // Frame Setting
        fobj.setSize(400,400);
        fobj.setLayout(null);
        fobj.setVisible(true);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }// End of PackerUnpackerGUI Constructor

/////////////////////////////////////////////////////////////////////////////////////////////
//
//  Function Name    :   AtionPerformed
//  Description      :   Handles button click events
//  Input            :   Nothing
//  Output           :   Nothing
//  Author           :   Rekha Shankarlal Kumawat
//  Date             :   14 / 02 / 2026
//
/////////////////////////////////////////////////////////////////////////////////////////////

    public void actionPerformed(ActionEvent aobj)
    {
        String FolderName = txtDirectory.getText();
        String PackedFileName = txtPackName.getText();
        int iRet = 0;

        /*--------------------------------------------------------
                  Pack Button Event
          --------------------------------------------------------
        */
       
        if(aobj.getSource() == btnPack)
        {   
            try
            {
                if(aobj.getSource() == btnPack)
                {
                    PackingActivity pobj = new PackingActivity(FolderName, PackedFileName);

                    // Check directory validity
                    iRet = pobj.CheckDirectory();

                    if(iRet == -1 )
                    {
                        ResultLable.setText("Result :- There is No Such Directory");
                        ResultLable.setForeground(Color.RED);
                        return ;
                    }

                    // Perform packing
                    iRet = pobj.MakePackFile();

                    if(iRet == -1)
                    {
                        ResultLable.setText("Result : There are no files in Directory");
                        ResultLable.setForeground(Color.RED);
                        return ;
                    }

                    ResultLable.setText("Result: Packing Successfull!");
                    ResultLable.setForeground(new Color(39,174,96));
                
                    // Enable Unpack button after successful packing
                    btnUnpack.setEnabled(true);
                }
            }

            catch(IOException eobj)
            {
                ResultLable.setText("Error occurred during packing.");
                ResultLable.setForeground(Color.RED);
            }

        }// End of Packing Event

        /*--------------------------------------------------------
                  Unpack Button Event
          --------------------------------------------------------
        */

        else if(aobj.getSource() == btnUnpack)
        {
            try
            {
                UnpackingActivity uobj = new UnpackingActivity(PackedFileName);

                // Perform Unpacking
                iRet = uobj.UnpackFile();

                if(iRet == -1)
                {
                    ResultLable.setText("Result: Unpacking Failed!");
                    ResultLable.setForeground(Color.RED);
                    return ;
                }

                ResultLable.setText("Result: Unpacking Successful!");
                ResultLable.setForeground(new Color(39,174,96));
            }
            catch(IOException eobj)
            {
                ResultLable.setText("Error Occured during Unpacking.");
                ResultLable.setForeground(Color.RED);

            }

        }// End of Unpacking Event

    }// end of ActionPerformed function

}//End of PackerUnpackerGUI class
public class GUI 
{
    public static void main(String A[])
    {
        new PackerUnpackerGUI();
    }
    
}
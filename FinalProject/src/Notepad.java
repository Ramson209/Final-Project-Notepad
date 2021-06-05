
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.awt.FileDialog;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.undo.CannotUndoException;

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;


//testArea which contain file information and menubar containing the menuitems like file,edit,format,view and help
@SuppressWarnings("deprecation")
public class Notepad extends KeyAdapter implements ActionListener,KeyListener
{
	static int active=0;
	static int fsize=17;
	
	JFrame frame1;
	JMenuBar npMenuBar;
	JMenu file,edit,format,view,help;
	JMenuItem newdoc,opendoc,exit,savedoc,saveasdoc,copydoc,pastedoc,fontfamily,fontsize,status,fontstyle,pagesetup,print,undo,delete,find,findnext,replace,Goto,selectall,time,cut,about,viewhelp;
	JTextArea maintext;
	JTextField title;
	Font font1;
	JPanel bottom;
	JLabel details,pastecopydoc;
	JList familylist,stylelist,sizelist;
	JScrollPane sb;
	
	String familyvalue[]= {"Agency FB","Antiqua","Architect","Arial","Calibri","Comic Sans","Courier","Cursive","Impact","Serif"};
	String sizevalue[]= {"5","10","15","20","25","30","35","40","45","50","55","60","65","70"};
	int [] stylevalue= {Font.PLAIN,Font.BOLD,Font.ITALIC};
	String []stylevalues= {"PLAIN","BOLD","ITALIC"};
	String ffamily,fsizestr,fstylestr;
	
	int fstyle;
	int cl;
	int linecount;
	String tle;
	String topicstitle ="";
	JScrollPane sp;
	;
	
	Notepad(){
		frame1=new JFrame("Notepad");
		
		font1=new Font("Arial",Font.PLAIN,17);
		
		bottom = new JPanel();
		details= new JLabel();
		pastecopydoc=new JLabel();
		
		familylist=new JList(familyvalue);
		stylelist=new JList(stylevalues);
		sizelist=new JList(sizevalue);
		
		familylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION	);
		sizelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stylelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		bottom.add(details);
		
		maintext= new JTextArea();
		
		sp=new JScrollPane(maintext);
		title= new JTextField(100);
		
		sb=new JScrollPane(maintext);
		
		maintext.setMargin(new Insets(5,5,5,5));
		
		maintext.setFont(font1);
		frame1.add(maintext);
		
		npMenuBar=new JMenuBar();
		
		file = new JMenu("File");
		edit= new JMenu("Edit");
		format=new JMenu("Format");
		view= new JMenu("View");
		help=new JMenu("Help");
		
		
		newdoc = new JMenuItem("New Ctrl+N");
		opendoc=new  JMenuItem("Open Ctrl+O");
		savedoc=new JMenuItem("Save Ctrl+S");
		saveasdoc=new JMenuItem("Save As... ");
		pagesetup=new JMenuItem("Page Setup...");
		print=new JMenuItem("Print Ctrl+P");
		exit=new JMenuItem("Exit");
		
		undo=new JMenuItem("Undo Ctrl+Z");
		cut=new JMenuItem("Cut Ctrl+X");
		copydoc=new JMenuItem("Copy Ctrl+C");
		pastedoc=new JMenuItem("Paste Ctrl+V");
		delete=new JMenuItem("Delete Del");
		find=new JMenuItem("Find Ctrl+F");
		findnext=new JMenuItem("Find Next F3");
		replace=new JMenuItem("Replace Ctrl+H");
		Goto=new JMenuItem("Go To Ctrl+G");
		selectall=new JMenuItem("Select Ctrl+A");
		time=new JMenuItem("Time/Date F5");
		
		
		
		fontfamily= new JMenuItem("Set Font Family");
	    fontstyle = new JMenuItem("Set Font Style");
		fontsize= new JMenuItem("Set Font Size");
		status = new JMenuItem("Status");
		
		viewhelp=new JMenuItem("View Help");
		about   = new JMenuItem("About NotePad");
		
		//FileMenu Items Adding
		file.add(newdoc);
		file.add(opendoc);
		file.add(savedoc);
		file.add(saveasdoc);
		file.add(pagesetup);
		file.add(print);
		file.add(exit);
		
		//EditMenu Items Adding
		edit.add(undo);
		edit.add(cut);
		edit.add(copydoc);
		edit.add(pastedoc);
		edit.add(delete);
		edit.add(find);
		edit.add(findnext);
		edit.add(replace);
		edit.add(Goto);
		edit.add(selectall);
		edit.add(time);
		
		
		
		//FormatMenu Items Adding
		format.add(fontfamily);
		format.add(fontstyle);
		format.add(fontsize);
		
		//ViewMenu Items Adding
		view.add(status);
		
		//HelpMenu Items Adding
		help.add(viewhelp);
		help.add(about);
		
		
		npMenuBar.add(file);
		npMenuBar.add(edit);
		npMenuBar.add(format);
		npMenuBar.add(view);
		npMenuBar.add(help);
		
		frame1.setJMenuBar(npMenuBar);
		frame1.add(bottom, BorderLayout.SOUTH);
		
		newdoc.addActionListener(this);
		opendoc.addActionListener(this);
		copydoc.addActionListener(this);
		pastedoc.addActionListener(this);
		
		status.addActionListener(this);
		savedoc.addActionListener(this);
		saveasdoc.addActionListener(this);
		pagesetup.addActionListener(this);
		print.addActionListener(this);
		
		undo.addActionListener(this);
		cut.addActionListener(this);
		copydoc.addActionListener(this);
		pastedoc.addActionListener(this);
		delete.addActionListener(this);
		find.addActionListener(this);
		findnext.addActionListener(this);
		replace.addActionListener(this);
		Goto.addActionListener(this);
		selectall.addActionListener(this);
		time.addActionListener(this);
		
		
		
		fontfamily.addActionListener(this);
		fontsize.addActionListener(this);
		fontstyle.addActionListener(this);
		
		exit.addActionListener(this);
		
		maintext.addKeyListener(this);
		
		frame1.setSize(600, 600);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	//ActionPerformed method

	
	public  void actionPerformed(ActionEvent ae)
	
		{   
		
		 
		// if the source was the new document option
			if(ae.getSource()==newdoc)
			{
				frame1.setTitle("New Document.txt");
				maintext.setText(" ");
			}
			
			// if the source was the copy document option
			else if (ae.getSource()== copydoc)
			{
				String texts=maintext.getText();
				pastecopydoc.setText(texts);
				JOptionPane.showMessageDialog(null, "Copy Text "+texts);
			}
			
			
			
			// if the source was the paste document option
			else if (ae.getSource() == pastedoc)
			{
				if(maintext.getText().length() !=0)
				{
					maintext.setText(maintext.getText());
				}
				
				else {
					maintext.setText(pastecopydoc.getText());
				}
			    }
			// if the source was the status option
				else if (ae.getSource()== status)
				{
					try {
						if(active==0)
						{
							File f =new File(tle+".txt");
							details.setText("Size: "+f.length());
						}
					}
						catch(Exception e) {
							
						}
					}
			
			
					else if (ae.getSource()== fontfamily) 
					{
						JOptionPane.showConfirmDialog(null,familylist,"Choose Font Family",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
						ffamily=String.valueOf(familylist.getSelectedValue());
						font1= new Font(ffamily,fstyle,fsize);
						maintext.setFont(font1);
					}
					else if (ae.getSource()== fontstyle) {
						JOptionPane.showConfirmDialog(null,stylelist,"Choose Font Style ",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
						fstyle=stylevalue[stylelist.getSelectedIndex()];
					    font1=new Font(ffamily,fstyle,fsize);
					    maintext.setFont(font1);            
					}
					else if (ae.getSource()==fontsize)
					{
						JOptionPane.showConfirmDialog(null,sizelist,"Choose Font Size",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
						fsizestr=String.valueOf(sizelist.getSelectedValue());
						fsize=Integer.parseInt(fsizestr);
						font1= new Font(ffamily,fstyle,fsize);
						maintext.setFont(font1);
					}
					
					else if(ae.getSource()==exit)
					{
						frame1.dispose();
					}
		}
			
	
	
			private void openFile()
	{
		// TODO Auto-generated method stub
		
	}

			//KeyTyped method. This method counts the letters and lines with Keyboard's key press
			public void keyTyped(KeyEvent ke) {
				cl=maintext.getText().length();
				linecount = maintext.getLineCount();
				details.setText("Lenght: "+cl+"Line: "+linecount);
			}
				
			public static void main(String[]args) {
				new Notepad();
			}
				}
			
			
			

		
			
		
		
		
		
		
	
	
	
	




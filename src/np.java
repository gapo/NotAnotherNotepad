/*
 *  Gautham Ponnu
 *  NAN
 *  not another notepad
 *  
 *  To Do
 *  ->      Make it sync with google docs
 *  ->      Change the layout Manager to Gridbaglayout
 * 
 *
 */
import java.awt.event.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class np extends JFrame implements ActionListener, KeyListener
{
	/**
	 * 
	 */
		private static final long serialVersionUID = -2055767696893025922L;
		
		// Declaration of Components
        JScrollPane scrollPane;
        ImageIcon icon;
        Image image;
        boolean txtChanged = false;
        boolean l=true;
        boolean music=true;
        
        //static SourceDataLine auline = null;
        static Clip auline=null;
        
        String fname = "";
        JToolBar tlbr1,tlbr2;
        ImageIcon iconNew, iconOpen, iconSave, iconQuit, iconHelp, iconedit;
        ImageIcon iconCut, iconCopy, iconPaste, iconfontup, iconfontdown, iconplay, iconstop;
        JButton bttnNew, bttnOpen, bttnSave, bttnQuit, bttnHelp, bttnedit, bttnedit1;
        JButton bttnCut, bttnCopy, bttnPaste, bttnfontup, bttnfontdown, bttnplay;
        JMenuBar mbar;
        JMenu mnuFile, mnuEdit, mnuHelp;
        JMenuItem fileNew, fileOpen, fileSave, fileExit;
        JMenuItem editCut, editCopy, editPaste, editSelectAll, editDel;
        JMenuItem helpAbout;
        
        int size=14;
        Font cur_font= new Font("Consolas", Font.PLAIN, size);
        
        JTextArea txtPad;

	public np()
	{
		int align=FlowLayout.CENTER;
                icon = new ImageIcon("images/bg.jpg");
                FlowLayout mylayout = new FlowLayout(align,200,100);
                JPanel panel = new JPanel(mylayout)
		{
            /**
			 * 
			 */
			private static final long serialVersionUID = 5911728837261070747L;

			@Override
			protected void paintComponent(Graphics g)
			{
				//  Dispaly image at at full size
				//g.drawImage(icon.getImage(), 0, 0, null);

				//  Scale image to size of component
				Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);

				//  Fix the image position in the scroll pane
//				Point p = scrollPane.getViewport().getViewPosition();
//				g.drawImage(icon.getImage(), p.x, p.y, null);

				super.paintComponent(g);
			}
		};
		panel.setOpaque( false );
		panel.setPreferredSize( new Dimension(400, 400) );
                scrollPane = new JScrollPane( panel );
		getContentPane().add( scrollPane );
                addWindowListener(new WinHandler());

                
                // The elusive toolbar
                initToolbar();
                
                txtPad = new JTextArea("NAN - Not Another Notepad",30,70);
                txtPad.selectAll();
                txtPad.setOpaque(false);
                txtPad.setFont(cur_font);
                txtPad.setForeground(Color.WHITE);
                txtPad.setLineWrap(true);
                txtPad.setWrapStyleWord(true);

                panel.add(txtPad);

                //add txtPad in scrollpane
                JScrollPane jscroll = new JScrollPane(txtPad);
                // Make the scrollbars never appear
                jscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                //add scrollpane in window
                jscroll.setOpaque(false);
                // God who created this viewport ! phew ... setting it to tranparent
                jscroll.getViewport().setOpaque(false);
                panel.add(jscroll);
                panel.add(tlbr1);
                		
	}

        void initMenu()
        {
        //create menubar
        mbar = new JMenuBar();

        //create menus
        mnuFile = new JMenu("File");
        mnuEdit = new JMenu("Edit");
        mnuHelp= new JMenu("Help");

        //create menuitems
        fileNew = new JMenuItem("New");
        fileOpen= new JMenuItem("Open");
        fileSave= new JMenuItem("Save");
        fileExit = new JMenuItem("Exit");

        editCut = new JMenuItem("Cut");
        editCopy= new JMenuItem("Copy");
        editPaste = new JMenuItem("Paste");
        editSelectAll = new JMenuItem("Select All");
        editDel= new JMenuItem("Delete");

        helpAbout= new JMenuItem("About");

        //add menuitems in menus
        mnuFile.add(fileNew);
        mnuFile.add(fileOpen);
        mnuFile.add(fileSave);
        mnuFile.add(fileExit);

        mnuEdit.add(editCut);
        mnuEdit.add(editCopy);
        mnuEdit.add(editPaste);
        mnuEdit.addSeparator();
        mnuEdit.add(editSelectAll);
        mnuEdit.add(editDel);

        mnuHelp.add(helpAbout);

        //attach menus to menubar
        mbar.add(mnuFile);
        mbar.add(mnuEdit);
        mbar.add(mnuHelp);

        //attach menubar to window
        setJMenuBar(mbar);


        //attach actionlister to menuitems
        fileNew.addActionListener(this);
        fileOpen.addActionListener(this);
        fileSave.addActionListener(this);
        fileExit.addActionListener(this);

        editCut.addActionListener(this);
        editCopy.addActionListener(this);
        editPaste.addActionListener(this);
        editSelectAll.addActionListener(this);
        editDel.addActionListener(this);

        helpAbout.addActionListener(this);
        }

        void initToolbar()
        {
        iconNew = new ImageIcon("images/new.png");
        iconOpen = new ImageIcon("images/open.png");
        iconSave = new ImageIcon("images/save.png");
        iconQuit = new ImageIcon("images/quit.png");
        iconHelp = new ImageIcon("images/help.png");
        // Auxillary Icons
        iconCut = new ImageIcon("images/cut.png");
        iconCopy = new ImageIcon("images/copy.png");
        iconPaste = new ImageIcon("images/paste.png");
        iconedit = new ImageIcon("images/edit.png");
        // Fonticons
        iconfontup = new ImageIcon("images/fontup.png");
        iconfontdown = new ImageIcon("images/fontdown.png");
        iconplay= new ImageIcon("images/play.png");
        iconstop= new ImageIcon("images/stop.png");
        
        bttnNew = new JButton(iconNew);
        bttnOpen = new JButton(iconOpen);
        bttnSave = new JButton(iconSave);
        bttnQuit = new JButton(iconQuit);
        bttnCut = new JButton(iconCut);
        bttnCopy = new JButton(iconCopy);
        bttnPaste = new JButton(iconPaste);
        bttnHelp = new JButton(iconHelp);
        bttnfontup = new JButton(iconfontup);
        bttnfontdown = new JButton(iconfontdown);
        bttnedit= new JButton(iconedit);
        bttnedit1= new JButton(iconedit);
        bttnplay=new JButton(iconstop);
        
        //Rollover Stuff
        bttnedit.setRolloverEnabled(true);
        
        
        bttnNew.setToolTipText("New");
        bttnOpen.setToolTipText("Open");
        bttnSave.setToolTipText("Save");
        bttnQuit.setToolTipText("Quit");
        bttnCut.setToolTipText("Cut");
        bttnCopy.setToolTipText("Copy");
        bttnPaste.setToolTipText("Paste");
        bttnHelp.setToolTipText("Help");
        bttnfontdown.setToolTipText("Decrease Font Size");
        bttnfontup.setToolTipText("Increase Font Size");
        bttnedit.setToolTipText(" Show me more ");
        bttnedit1.setToolTipText(" No ! Hide these ");
        bttnplay.setToolTipText("Stop the waves");
        
        
        // Making the button transparent
        bttnNew.setOpaque(false);
        bttnOpen.setOpaque(false);
        bttnSave.setOpaque(false);
        bttnQuit.setOpaque(false);
        bttnCut.setOpaque(false);
        bttnCopy.setOpaque(false);
        bttnPaste.setOpaque(false);
        bttnHelp.setOpaque(false);
        bttnfontdown.setOpaque(false);
        bttnfontup.setOpaque(false);
        bttnedit.setOpaque(false);
        bttnedit1.setOpaque(false);
        bttnplay.setOpaque(false);
        
        //Toolbar - Passing 1 to make orientation vertical
        tlbr1 = new JToolBar(1);
        tlbr1.setFloatable(false);
        tlbr2 = new JToolBar();
        tlbr2.setFloatable(false);
        tlbr2.setVisible(false);
        
        // Damn the toolbar it's should be transparent
        tlbr1.setOpaque(false);
        tlbr2.setOpaque(false);

        //add buttons into toolbar
        tlbr1.add(bttnNew);
        tlbr1.add(bttnOpen);
        tlbr1.add(bttnSave);
        
        tlbr1.addSeparator();
        tlbr1.add(bttnedit);
        tlbr1.add(tlbr2);
        tlbr1.add(bttnplay);
        tlbr1.addSeparator();
        tlbr1.add(bttnHelp);
        tlbr1.add(bttnQuit);
        
        tlbr2.add(bttnedit1);
        tlbr2.add(bttnCut);
        tlbr2.add(bttnCopy);
        tlbr2.add(bttnPaste);
        tlbr2.add(bttnfontup);
        tlbr2.add(bttnfontdown);
        
        
        tlbr1.setRollover(true);
        tlbr2.setRollover(true);

        //attach actionlistener to buttons
        bttnNew.addActionListener(this);
        bttnOpen.addActionListener(this);
        bttnSave.addActionListener(this);
        bttnQuit.addActionListener(this);
        bttnCut.addActionListener(this);
        bttnCopy.addActionListener(this);
        bttnPaste.addActionListener(this);
        bttnHelp.addActionListener(this);
        bttnfontup.addActionListener(this);
        bttnfontdown.addActionListener(this);
        bttnedit.addActionListener(this);
        bttnedit1.addActionListener(this);
        bttnplay.addActionListener(this);
        
        }

        public void actionPerformed(ActionEvent e)
        {
        Object src = e.getSource();
        if(src.equals(bttnNew) || src.equals(fileNew))
        {
        newFile();
        }
        else if(src.equals(bttnOpen) || src.equals(fileOpen))
        {
        openFile();
        }
        else if(src.equals(bttnSave) || src.equals(fileSave))
        {
        saveFile();
        }
        else if(src.equals(bttnQuit) || src.equals(fileExit))
        {
        exitFile();
        }
        else if(src.equals(fileExit))
        {
        exitFile();
        }
        else if(src.equals(bttnCut) || src.equals(editCut))
        {
        txtPad.cut();
        }
        else if(src.equals(bttnCopy) || src.equals(editCopy))
        {
        txtPad.copy();
        }
        else if(src.equals(bttnPaste) || src.equals(editPaste))
        {
        txtPad.paste();
        }
        else if(src.equals(bttnfontup))
        {
            cur_font=txtPad.getFont();
            fontup();
        }
         else if(src.equals(bttnfontdown))
        {
            cur_font=txtPad.getFont();
            fontdown();
        }
         else if(src.equals(bttnedit) || src.equals(bttnedit1))
         {
             tlbr2.setVisible(l);
        	 l=!l;
        	 bttnedit.setVisible(l);
         }
        else if(src.equals(bttnHelp))
        {
        aboutHelp();
        }
        else if(src.equals(bttnplay))
        {
        	music=!music;
        	if(music==false)
        	{
        		bttnplay.setIcon(iconplay);
        		bttnplay.setToolTipText("Let there be sound");
        		bgstop();
        	}
        	else
        		{
        		bttnplay.setIcon(iconstop);
        		bttnplay.setToolTipText("Stop the waves");
        		bgstart();
        	}
        	
        }
        }//end of actionPerformed



        void newFile()
        {
        if(txtChanged == true)
        {
        int res;
        res = JOptionPane.showConfirmDialog
        (
        this, //parent
        "Do You Want To Save Changes",
        "File New",
        JOptionPane.YES_NO_CANCEL_OPTION
        );

        if(res == JOptionPane.YES_OPTION)
        {
        saveFile();
        }
        else if(res == JOptionPane.CANCEL_OPTION)
        {
        return;//terminate fn
        //so that dispose() is not called
        }

        //no need to handle no as nothing is to be
        //saved on no and only exit has tobe peformed
        }
        //code for file new
        fname = "";
        txtChanged = false;
        txtPad.setText("");

        }

        void saveFile()
        {
        if(fname.equals(""))
        {
        JFileChooser jfc = new JFileChooser();
        int res;
        res = jfc.showSaveDialog(this);
        // Made a change here . Added "JFileChooser."
        if(res == JFileChooser.APPROVE_OPTION)
        {
        fname = jfc.getSelectedFile().getAbsolutePath();
        }
        else
        {//cancelled save
        return;
        }
        }

        try
        {
        FileWriter fw = new FileWriter(fname);
        fw.write(txtPad.getText());
        fw.flush();
        fw.close();

        //file is saved
        txtChanged = false;
        }
        catch(Exception ex)
        {
        JOptionPane.showMessageDialog
        (
        this,
        ex.getMessage(),
        "Save Err",
        JOptionPane.ERROR_MESSAGE
        );
        }

        }

        void openFile()
        {
        //JColorChooser jcc = new JColorChooser();
        //Color c = jcc.showDialog(this,"dfdf",Color.RED);
        //txtPad.setForeground(c);

        int res;
        JFileChooser jfc = new JFileChooser("c:/");
        res = jfc.showOpenDialog(this);//parent window
        if(res == JFileChooser.APPROVE_OPTION)
        {//open
        fname = jfc.getSelectedFile().getAbsolutePath();

        try
        {
        FileReader fr = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);

        //clear existing content of text area
        txtPad.setText("");
        String s;

        while( (s = br.readLine()) != null )
        {
        txtPad.append(s);
        txtPad.append("\n");
        }
        br.close();
        fr.close();
        }
        catch(Exception ex)
        {
        JOptionPane.showMessageDialog
        (
        this, //parent window
        ex.getMessage(), //msg
        "Open Error", //title
        JOptionPane.ERROR_MESSAGE //icon
        );
        }

        }
        }
        void exitFile()
        {

            if(txtChanged == true)
                {
                    int res;
                    res = JOptionPane.showConfirmDialog
                    (
                    this, //parent
                    "Do You Want To Save Changes",
                    "File Exit",
                    JOptionPane.YES_NO_CANCEL_OPTION
                    );

                    if(res == JOptionPane.YES_OPTION)
                    {
                    saveFile();
                    }
                    else if(res == JOptionPane.CANCEL_OPTION)
                    {
                    return;//terminate fn
                    //so that dispose() is not called
                    }

                    //no need to handle no as nothing is to be
                    //saved on no and only exit has tobe peformed
                }
            dispose();//exit
            System.exit(1);
        }

        void fontup()
        {
            size=cur_font.getSize();
            if(size<40)
            {
                size=size+4;
            }
            Font f=new Font("Consolas", Font.PLAIN, size);
            txtPad.setFont(f);
        }

         void fontdown()
        {
            size=cur_font.getSize();
            if(size>6)
            {
                size=size-4;
            }
            Font f=new Font("Consolas", Font.PLAIN, size);
            txtPad.setFont(f);
        }

        void aboutHelp()
        {
        new HelpDlg(this);
        }

        public void keyPressed(KeyEvent e)
        {
        //System.out.println("KP");
        }

        public void keyReleased(KeyEvent e)
        {
        //System.out.println("KR");
        }

        public void keyTyped(KeyEvent e)
        {
        txtChanged = true;
        }

        class WinHandler extends WindowAdapter
        {
              @Override
        public void windowClosing(WindowEvent e)
        {
        exitFile();
        }
        }
        // Enjoy the Ocean babe !
        static void bgsound(){
        	String filename="bg.wav";
        	File soundFile = new File(filename);

        	if (!soundFile.exists())
        	{
        	 System.err.println("Wave file not found: " + filename);
        	 return;
        	}
        	AudioInputStream audioInputStream = null;
        	try
        	{
        	 audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        	}
        	catch(Exception e)
        	{
        	 e.printStackTrace();
        	 return;
        	}
        	
        	AudioFormat format = audioInputStream.getFormat();

        	//Describe a desired line
        	DataLine.Info info = new DataLine.Info(Clip.class, format);
        	
        	try{
        		auline=(Clip)AudioSystem.getLine(info);
        		auline.open(audioInputStream);
        		auline.setLoopPoints(0,-1);
        		auline.loop(-1);
        		
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
           	 	return;
        	}        
        	
        } 
    static void bgstop(){
    	try{
    	auline.stop();
    	}
    	catch(Exception e)
    	{
    		System.err.println(e);
    	}
    }
    
    static void bgstart(){
    	auline.loop(-1);
    }
        
	public static void main (String [] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(false);
        JDialog.setDefaultLookAndFeelDecorated(false);
                try
                {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }
                catch (Exception ex)
                {
                        System.out.println("U wanted looks na ... Now get this: ");
                        System.out.println(ex);
                }

        // Making it fullscreen
        np frame = new np();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setUndecorated(true);
		//frame.setLocationRelativeTo( null );
		frame.setVisible(true);
		
		// It's ROck and Roll time
		bgsound();
		
	}
}

//
//  tessMain.java
//
//  Main class for tessellation assignment.
//
//  Students should not be modifying this file.
//
//  Updated 2014/10 by Vasudev Prasad Bethamcherla.
//

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;


public class tessMain implements GLEventListener, KeyListener
{
    /**
     * static values
     */
    public final static int CUBE = 0;
    public final static int CYLINDER = 1;
    public final static int CONE = 2;
    public final static int SPHERE = 3;


    /**
     * values for levels of subdivisions
     */
    private int division1 = 3;
    private int division2 = 3;
    private int currentShape = tessMain.CUBE;

    /**
     * buffer info
     */
    private boolean bufferInit = false;
    private int vbuffer;
    private int ebuffer;

    /**
     * rotation angles
     */
    public int theta;
    public float angles[];
    private float angleInc = 5.0f;

    /**
     * shader info
     */
    private shaderSetup myShaders;
    private int shaderProgID = 0;
    private boolean updateNeeded = true;

    /**
     * shape info
     */
    cgShape myShape;

    /**
     * my canvas
     */
    GLCanvas myCanvas;

    /**
     * constructor
     */
    public tessMain(GLCanvas G)
    {
        angles = new float[3];
        angles[0] = 30.0f;
        angles[1] = 30.0f;
        angles[2] = 0.0f;

        myShaders = new shaderSetup();
        myShape = new cgShape();
        myCanvas = G;

        G.addGLEventListener (this);
        G.addKeyListener (this);
    }

    private void errorCheck (GL2 gl2)
    {
        int code = gl2.glGetError();
        if (code == GL.GL_NO_ERROR)
            System.err.println ("All is well");
        else
            System.err.println ("Problem - error code : " + code);

    }


    /**
     * Called by the drawable to initiate OpenGL rendering by the client.
     */
    public void display(GLAutoDrawable drawable)
    {
        // get GL
        GL2 gl2 = (drawable.getGL()).getGL2();

        // This should all probably be in createNewShape...  However,
        // since we can only get access to the GL2 during display, we'll
        // have to include it here.
        if (updateNeeded) {

            // get your vertices and elements
            Buffer points = myShape.getVertices();
            Buffer elements = myShape.getElements();

            // set up the vertex buffer
            int bf[] = new int[1];
            if (bufferInit) {
                bf[0] = vbuffer;
                gl2.glDeleteBuffers(1, bf, 0);
            }
            gl2.glGenBuffers (1, bf, 0);
            vbuffer = bf[0];
            long vertBsize = myShape.getNVerts() * 4l * 4l;
            gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer);
            gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize , points, GL.GL_STATIC_DRAW);
            bufferInit = true;

            // set up element buffer.
            if (bufferInit) {
                bf[0] = ebuffer;
                gl2.glDeleteBuffers(1, bf, 0);
            }
            gl2.glGenBuffers (1, bf, 0);
            ebuffer = bf[0];
            long eBuffSize = myShape.getNVerts() * 2l;
            gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer);
            gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize,elements,
                              GL.GL_STATIC_DRAW);

            // we're all done
            updateNeeded = false;
        }

        // clear the frame buffer
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );

        // bind our vertex buffer
        gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer);

        // bind our element array buffer
        gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer);

        // set up our attribute variables
        gl2.glUseProgram (shaderProgID);
        int  vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
        gl2.glEnableVertexAttribArray ( vPosition );
        gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
                                   0, 0l);

        // pass in our rotations as a uniform variable
        theta = gl2.glGetUniformLocation (shaderProgID, "theta");
        gl2.glUniform3fv (theta, 1, angles, 0);

        // draw our shapes
        int nElems = myShape.getNVerts();
        gl2.glDrawElements ( GL.GL_TRIANGLES, nElems,  GL.GL_UNSIGNED_SHORT, 0l);

    }


    /**
     * Notifies the listener to perform the release of all OpenGL
     * resources per GLContext, such as memory buffers and GLSL
     * programs.
     */
    public void dispose(GLAutoDrawable drawable)
    {

    }

    /**
     * Called by the drawable immediately after the OpenGL context is
     * initialized.
     */
    public void init(GLAutoDrawable drawable)
    {
        // get the gl object
        GL2 gl2 = drawable.getGL().getGL2();

        // Load shaders
        shaderProgID = myShaders.readAndCompile (gl2, "shader.vert", "shader.frag");
        if (shaderProgID == 0) {
            System.err.println ("Error setting up shaders");
            System.exit (1);
        }

        // Other GL initialization
        gl2.glEnable( GL.GL_DEPTH_TEST );
        gl2.glEnable( GL.GL_CULL_FACE );
        gl2.glCullFace( GL.GL_BACK );
        gl2.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE );
        gl2.glFrontFace( GL.GL_CCW );
        gl2.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
        gl2.glDepthFunc( GL.GL_LEQUAL );
        gl2.glClearDepth( 1.0f );

        // initially create a new Shape
        createNewShape();

    }

    /**
     * Called by the drawable during the first repaint after the component
     * has been resized.
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                     int height)
    {

    }



    /**
     * creates a new shape
     */
    public void createNewShape()
    {
        // clear the old shape
        myShape.clear();

        // create the new shape...should be a switch here
        switch (currentShape)
        {
            case CUBE: myShape.makeCube (division1);
                break;

            case CYLINDER: myShape.makeCylinder (0.5f, division1, division2);
                break;

            case CONE: myShape.makeCone (0.5f, division1, division2);
                break;

            case SPHERE: myShape.makeSphere (0.5f, division1, division2);
                break;
        }

        updateNeeded = true;
    }

    /**
     * Because I am a Key Listener...we'll only respond to key presses
     */
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    /** 
     * Rotates the shapes along x,y,z.
     * 
     * Causes gimbal lock which also happened on Apollo 11
     * http://en.wikipedia.org/wiki/Gimbal_lock#Gimbal_lock_on_Apollo_11
     * Solution? Use Quaternions (Taught in Comp. Animation: Algorithms)
     * 
     * TIDBIT:
     * Quaternion plaque on Brougham (Broom) Bridge, Dublin, which says:
     * 
     *  "Here as he walked by
     *  on the 16th of October 1843
     *  
     *  Sir William Rowan Hamilton 
     *  
     *  in a flash of genius discovered
     *  the fundamental formula for
     *  quaternion multiplication
     *  i^2 = j^2 = k^2 = ijk = -1
     *  & cut it on a stone of this bridge"
     **/
    public void animate() {

        for( int i = 0; i < 150; i++ ) {
            angles[0] += angleInc / 3;
            myCanvas.display();
        }
        for( int i = 0; i < 150; i++ ) {
            angles[1] += angleInc / 3;
            myCanvas.display();
        }
        for( int i = 0; i < 150; i++ ) {
            angles[2] += angleInc / 3;
            myCanvas.display();
        }
    }

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
        // Get the key that was pressed
        char key = e.getKeyChar();

        // Respond appropriately
        switch( key ) {

	    // automated animation
            case 'a': animate(); break;

	    // incremental rotation along the axes
            case 'x': angles[0] -= angleInc; break;
            case 'y': angles[1] -= angleInc; break;
            case 'z': angles[2] -= angleInc; break;
            case 'X': angles[0] += angleInc; break;
            case 'Y': angles[1] += angleInc; break;
            case 'Z': angles[2] += angleInc; break;

	    // shape selection
            case 'c' : currentShape = CUBE; createNewShape(); break;
            case 'C' : currentShape = CYLINDER; createNewShape(); break;
            case 'n' : currentShape = CONE; createNewShape(); break;
            case 's' : currentShape = SPHERE; createNewShape(); break;

	    // tessellation factors
            case '+': division1++; createNewShape(); break;
            case '=': division2++; createNewShape(); break;
            case '-': if( division1 > 1 ) {
	                division1--;
			createNewShape();
		      }
		      break;
            case '_': if( division2 > 1 ) {
                        division2--;
                        if (currentShape != CUBE) createNewShape();
                      }
                      break;

	    // termination
	    case 033:  // Escape key
            case 'q': case 'Q':
                System.exit( 0 );
                break;
        }

        // do a redraw
        myCanvas.display();
    }


    /**
     * main program
     */
    public static void main(String [] args)
    {
        // GL setup
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        // create your tessMain
        tessMain myMain = new tessMain(canvas);

        Frame frame = new Frame("CG - Tessellation Assignment");
        frame.setSize(512, 512);
        frame.add(canvas);
        frame.setVisible(true);

        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}

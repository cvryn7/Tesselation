//
//  cgShape.java
//
//  Class that includes routines for tessellating a number of basic shapes.
//
//  Students are to supply their implementations for the functions in
//  this file using the function "addTriangle()" to do the tessellation.
//

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;

/*
 * Modified by Karan Bhagat
 * Modified on April 13 2015
 */
public class cgShape extends simpleShape
{
    /**
     * constructor
     */
    public cgShape()
    {
    	
    }

    //this method creates tessalated sides for the cube
    //this method take parameters for one side at a time 
    // and create one side.
    void createCubeTessellation(float x, float y, float z, float sideLen, int subdivisions, int pos, char fixedCoord){
    	float newX,newY,newZ,newSideLen;
    	
    	//create side of cube where x is fixed coordinate
    	if( fixedCoord == 'x' ){
    		//adding triangles horizontally in z direction
    		//with increment in y after every z length is complete.
    		for( int i = 0 ; i < subdivisions; i++ ){
    			for( int j = 0; j < subdivisions; j++ ){
    				
    				newSideLen = sideLen/subdivisions;
    				//for side in positive x-axis
    				if( pos == 1){
    					newY = y + i*(sideLen/subdivisions);
        				newZ = z - j*(sideLen/subdivisions);
    					
    	    			addTriangle(x,newY,newZ, x,newY,newZ-newSideLen, x,newY+newSideLen,newZ-newSideLen);
    	    			addTriangle(x,newY,newZ, x,newY+newSideLen,newZ-newSideLen, x,newY+newSideLen,newZ);
    	    		}
    				//for side in -ve x-axis
    				else{
    	    			newY = y + i*(sideLen/subdivisions);
        				newZ = z + j*(sideLen/subdivisions);
    	    			
    	    			addTriangle(x,newY,newZ, x,newY,newZ+newSideLen, x,newY+newSideLen,newZ+newSideLen);
    	    			addTriangle(x,newY,newZ, x,newY+newSideLen,newZ+newSideLen, x,newY+newSideLen,newZ);
    	    		}
    			}
    		}
    	}
    	//create side of cube where y is fixed coordinate
    	else if( fixedCoord == 'y'){
    		//adding triangles horizontally in x direction
    		//with increment in z after every x length is complete.
    		for( int i = 0 ; i < subdivisions; i++ ){
    			for( int j = 0; j < subdivisions; j++ ){

    				
    				newSideLen = sideLen/subdivisions;
    				//for side in positive y-axis
    				if( pos == 1){
        				newX = x + i*(sideLen/subdivisions);
        				newZ = z - j*(sideLen/subdivisions);
    					
    	    			addTriangle(newX,y,newZ, newX+newSideLen,y,newZ-newSideLen, newX,y,newZ-newSideLen);
    	    			addTriangle(newX,y,newZ, newX+newSideLen,y,newZ, newX+newSideLen,y,newZ-newSideLen);
    	    		}
    				//for side in -ve y-axis
    				else{
        				newX = x - i*(sideLen/subdivisions);
        				newZ = z - j*(sideLen/subdivisions);
    	    			
    	    			addTriangle(newX,y,newZ, newX-newSideLen,y,newZ-newSideLen, newX,y,newZ-newSideLen);
    	    			addTriangle(newX,y,newZ, newX-newSideLen,y,newZ, newX-newSideLen,y,newZ-newSideLen);
    	    		}
    			}
    		}
    	}
    	//create side of cube where z is fixed coordinate
    	else{
    		//adding triangles horizontally in x direction
    		//with increment in y after every x length is complete.
    		for( int i = 0 ; i < subdivisions; i++ ){
    			for( int j = 0; j < subdivisions; j++ ){
    				newSideLen = sideLen/subdivisions;
    				//for side in positive z-axis
    				if( pos == 1){
    					newX = x + j*(sideLen/subdivisions);
        				newY = y + i*(sideLen/subdivisions);
        				    					
    					addTriangle(newX,newY,z, newX+newSideLen,newY,z, newX+newSideLen,newY+newSideLen,z);
    	    			addTriangle(newX,newY,z, newX+newSideLen,newY+newSideLen,z, newX,newY+newSideLen,z);
    	    		}
    				//for side in -ve z-axis
    				else{
    	    			newX = x - j*(sideLen/subdivisions);
        				newY = y + i*(sideLen/subdivisions);
        				
    	    			addTriangle(newX,newY,z, newX-newSideLen,newY,z, newX-newSideLen,newY+newSideLen,z);
    	    			addTriangle(newX,newY,z, newX-newSideLen,newY+newSideLen,z, newX,newY+newSideLen,z);
    	    		}
    			}
    		}
    	}
    	
    	
    }
    
    /**
     * makeCube - Create a unit cube, centered at the origin, with a given
     * number of subdivisions in each direction on each face.
     *
     * @param subdivision - number of equal subdivisons to be made in each
     *        direction along each face
     *
     * Can only use calls to addTriangle()
     */
    public void makeCube (int subdivisions)
    {
        if( subdivisions < 1 )
            subdivisions = 1;

        // YOUR IMPLEMENTATION HERE
        float x,y,z,sideLen;
        int position; // 1 : front facing, 0: back facing
        char fixedCoord;
        sideLen = 1.0f;
        
        //drawing front side of the cube
        x = -0.5f;
        y = -0.5f;
        z = 0.5f;
        position = 1;//on positive z axis
        fixedCoord = 'z';
        createCubeTessellation(x,y,z,sideLen,subdivisions,position,fixedCoord);
        
        //drawing back face of the cube
        x = 0.5f;
        y = -0.5f;
        z = -0.5f;
        position = 0;//on -ve z axis
        fixedCoord = 'z';
        createCubeTessellation(x,y,z,sideLen,subdivisions,position,fixedCoord);
        
        //drawing right face of the cube
        x = 0.5f;
        y = -0.5f;
        z = 0.5f;
        position = 1;//on positive x axis
        fixedCoord = 'x';
        createCubeTessellation(x,y,z,sideLen,subdivisions,position,fixedCoord);
        
        //drawing left face of the cube
        x = -0.5f;
        y = -0.5f;
        z = -0.5f;
        position = 0;//on -ve x axis
        fixedCoord = 'x';
        createCubeTessellation(x,y,z,sideLen,subdivisions,position,fixedCoord);
        
        //drawing top face of the cube
        x = -0.5f;
        y = 0.5f;
        z = 0.5f;
        position = 1;//on positive y axis
        fixedCoord = 'y';
        createCubeTessellation(x,y,z,sideLen,subdivisions,position,fixedCoord);
        
        //drawing bottom face of the cube
        x = 0.5f;
        y = -0.5f;
        z = 0.5f;
        position = 0;//on -ve y axis
        fixedCoord = 'y';
        createCubeTessellation(x,y,z,sideLen,subdivisions,position,fixedCoord);
        
        //this for drawing basic cube without with one subdivision
       /* addTriangle(0.5f,0.5f,0.5f, -0.5f,0.5f,0.5f, 0.5f,-0.5f,0.5f );
        addTriangle(-0.5f,0.5f,0.5f, -0.5f,-0.5f,0.5f, 0.5f,-0.5f,0.5f );
        
        addTriangle(0.5f,0.5f,-0.5f, 0.5f,-0.5f,-0.5f, -0.5f,0.5f,-0.5f );
        addTriangle(-0.5f,0.5f,-0.5f, 0.5f,-0.5f,-0.5f, -0.5f,-0.5f,-0.5f );
        
        addTriangle(0.5f,0.5f,0.5f, 0.5f,-0.5f,0.5f, 0.5f,-0.5f,-0.5f );
        addTriangle(0.5f,0.5f,0.5f, 0.5f,-0.5f,-0.5f, 0.5f,0.5f,-0.5f );
        
        addTriangle(-0.5f,0.5f,0.5f, -0.5f,-0.5f,-0.5f, -0.5f,-0.5f,0.5f );
        addTriangle(-0.5f,0.5f,0.5f, -0.5f,0.5f,-0.5f, -0.5f,-0.5f,-0.5f );
        
        addTriangle(0.5f,0.5f,0.5f, -0.5f,0.5f,-0.5f, -0.5f,0.5f,0.5f );
        addTriangle(0.5f,0.5f,0.5f, 0.5f,0.5f,-0.5f, -0.5f,0.5f,-0.5f );
        
        addTriangle(0.5f,-0.5f,0.5f, -0.5f,-0.5f,0.5f, -0.5f,-0.5f,-0.5f );
        addTriangle(0.5f,-0.5f,0.5f, -0.5f,-0.5f,-0.5f, 0.5f,-0.5f,-0.5f );
        */
    }

    /**
     * makeCylinder - Create polygons for a cylinder with unit height, centered
     * at the origin, with separate number of radial subdivisions and height
     * subdivisions.
     *
     * @param radius - Radius of the base of the cylinder
     * @param radialDivision - number of subdivisions on the radial base
     * @param heightDivisions - number of subdivisions along the height
     *
     * Can only use calls to addTriangle()
     */
    public void makeCylinder (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
            radialDivisions = 3;

        if( heightDivisions < 1 )
            heightDivisions = 1;
        
        
        
        // YOUR IMPLEMENTATION HERE
        final double fullAngle = 360;
        final double PI = 3.14159265f;
        
        //finding the central angle of each triangle in the base
        double central_angle = (fullAngle/radialDivisions)*(PI/180); // in radians
        
        float y = 0.5f;       
        
        // circumference coordinates
        float circ1X = radius;
        float circ1Z = 0f;
        
        float circ2X;
        float circ2Z;
        
        //finding height of each subdivision i.e. incement in y
        float newDivHeight = 1.0f/heightDivisions; //new height of each divisions
        
        //loop over radial divisions to finding next coordinates on the circumference
        for(int i = 0; i < radialDivisions; i++){
        	//getting next coordinates on circumference.
        	//formulae to find coordinates on circumference
        	// x = r*cos(alpha)
        	// z = r*sin(alpha)
        	circ2X = (float)(radius*Math.cos(central_angle*(i+1)));
        	circ2Z = (float)(radius*Math.sin(central_angle*(i+1)));
        	
        	
        	// drawing upper circle (upper base)
        	y = 0.5f;
        	addTriangle(0f,y,0f, circ2X,y,circ2Z, circ1X,y,circ1Z);
        	
        	// drawing lower circle (lower abse)
        	y = -0.5f;
        	addTriangle(0f,y,0f, circ1X,y,circ1Z, circ2X,y,circ2Z);
        	
        	
        	
        	//drawing curved surface
        	for(int j = 0; j < heightDivisions; j++){
        		
        		y = -0.5f + (newDivHeight)*j;
        		
        		addTriangle(circ2X,y,circ2Z, circ1X,y,circ1Z, circ1X,y+newDivHeight,circ1Z);
        		addTriangle(circ2X,y,circ2Z, circ1X,y+newDivHeight,circ1Z, circ2X,y+newDivHeight,circ2Z);
        	}
        	
        	//passing new found circumference coordinates to 
        	//old cirumference coordinates to use in next triangle drawing on base.
        	circ1X = circ2X;
        	circ1Z = circ2Z;
        }
        

       
    }

    /**
     * makeCone - Create polygons for a cone with unit height, centered at the
     * origin, with separate number of radial subdivisions and height
     * subdivisions.
     *
     * @param radius - Radius of the base of the cone
     * @param radialDivision - number of subdivisions on the radial base
     * @param heightDivisions - number of subdivisions along the height
     *
     * Can only use calls to addTriangle()
     */
    public void makeCone (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
            radialDivisions = 3;

        if( heightDivisions < 1 )
            heightDivisions = 1;

        // YOUR IMPLEMENTATION HERE
        final double fullAngle = 360;
        final double PI = 3.14159265f;
        
        //finding central angle of the triangles of the base
        double central_angle = (fullAngle/radialDivisions)*(PI/180); // in radians
        
        float y = 0.5f;
        
        // circumference coordinates
        
        float circ1X = radius;
        float circ1Z = 0f;
        
        float circ2X;
        float circ2Z;
        
        //divinding the radius to get the 
        //decrease in x cordinate or in radius with 
        // as the height increases from bottom to up.
        float radiusDivLen = 0.5f/heightDivisions;
        
        //these slant coordinates are used to
        //draw curved surface of cone. these are
        //X coordinates of each four sided polygon
        //divided in two triangles and 1,2,3,4 denotes
        //X coordinates taken in clockwise order
        //where slant1X denotes bottom right X cordinate
        //of each four sided polygon.
        //in starting slant1X = circ1X and 
        //slant2X will be determined , slant3X will determined
        //in the loop  and slant4X is 
        //radiusDivLen minus circ1X in starting.
        
        float slant1X = circ1X;
        float slant2X;
        float slant3X;
        float slant4X = circ1X-radiusDivLen;
        
        //same explanation as for slantX , only slant4Z will have
        //same cordinate as circ1Z and changing along x in starting
        // only and no change along z.
        float slant1Z = circ1Z;
        float slant2Z;
        float slant3Z;
        float slant4Z = circ1Z;
        
        float newDivHeight = 1.0f/heightDivisions; //new height of each divisions
        
        
        //looping through the radial divisions 
        for(int i = 0; i < radialDivisions; i++){
        	
        	//finding new circumference coordinates
        	circ2X = (float)(radius*Math.cos(central_angle*(i+1)));
        	circ2Z = (float)(radius*Math.sin(central_angle*(i+1)));
        	
        	// drawing lower circle (lower abse)
        	y = -0.5f;
        	addTriangle(0f,y,0f, circ1X,y,circ1Z, circ2X,y,circ2Z);
        	
        	
        	
        	//drawing curved surface
        	slant1X = circ1X;
        	slant1Z = circ1Z;
        	slant2X = circ2X;
        	slant2Z = circ2Z;
        	for(int j = 0; j < heightDivisions; j++){
        		//circumference coordinates of the new smaller circle which can be seen as 
        		//new base. then drawing tesselated four sided polygon using cicumference
        		//coordinates of new base and old base.And these coordinates are called as
        		//slant coordinates
        		slant3X = (float)((radius-radiusDivLen*(j+1))*Math.cos(central_angle*(i+1)));
        		slant3Z = (float)((radius-radiusDivLen*(j+1))*Math.sin(central_angle*(i+1)));
        		slant4X = (float)((radius-radiusDivLen*(j+1))*Math.cos(central_angle*(i)));
        		slant4Z = (float)((radius-radiusDivLen*(j+1))*Math.sin(central_angle*(i)));
        		y = -0.5f + (newDivHeight)*j;
        		
        		addTriangle(slant2X,y,slant2Z, slant1X,y,slant1Z, slant4X,y+newDivHeight,slant4Z);
        		addTriangle(slant2X,y,slant2Z, slant4X,y+newDivHeight,slant4Z, slant3X,y+newDivHeight,slant3Z);
        		
        		//passing new slant(new base) coordinates as old coordinates
        		slant1X = slant4X;
        		slant2X = slant3X;
        		slant1Z = slant4Z;
        		slant2Z = slant3Z;
        	}
        	
        	circ1X = circ2X;
        	circ1Z = circ2Z;
        }
    }

    /**
     * makeSphere - Create sphere of a given radius, centered at the origin,
     * using spherical coordinates with separate number of thetha and
     * phi subdivisions.
     *
     * @param radius - Radius of the sphere
     * @param slides - number of subdivisions in the theta direction
     * @param stacks - Number of subdivisions in the phi direction.
     *
     * Can only use calls to addTriangle
     */
    public void makeSphere (float radius, int slices, int stacks)
    {
        if( slices < 3 )
            slices = 3;

        if( stacks < 3 )
            stacks = 3;

        // YOUR IMPLEMENTATION HERE
        //two case when stacks are odd or even.
        if(stacks%2 == 0){
        	//in even stack , can be started from middle and
        	//then progress till the upper of the dome.
        	makeSphereEvenStacks(radius,slices,stacks);
        }else{
        	//in odd stack , have to start from little above the
        	// middle at from angle phi/2. and in the end draw
        	//the middle part from -phi/2 to phi/2.
        	makeSphereOddStacks(radius,slices,stacks);
        }
       
       
    }

	private void makeSphereOddStacks(float radius, int slices, int stacks) {
		// TODO Auto-generated method stub
			final double fullAngle = 360;
	        final double halfAngle = 180;
	        final double PI = 3.14159265f;
	        
	        // theta i.e. angle along the latitude
	        double central_theta_angle = (fullAngle/slices)*(PI/180); // in radians
	        
	        //phi angle along the longitude
	        double central_phi_angle = (halfAngle/stacks)*(PI/180); // in radians
	        
	        float y;
	        // circumference coordinates
	        
	        float circ1X;
	        float circ1Z;
	        
	        float circ2X;
	        float circ2Z;
	        

	        //radius of the current longitude
	        float crntRadius = (float)(radius*Math.cos(central_phi_angle/2));
	        float newRadius ;
	        
	        circ1X = crntRadius;
	        circ1Z = 0;
	        float crntY = (float)(radius*Math.sin(central_phi_angle/2));
	        float newY;
	        //these slant coordinates are used to
	        //draw curved surface of cone. these are
	        //X coordinates of each four sided polygon
	        //divided in two triangles and 1,2,3,4 denotes
	        //X coordinates taken in clockwise order.
	        float slant1X = circ1X;
	        float slant2X;
	        float slant3X;
	        float slant4X;
	        
	        //same explanation as for slantX , only slant4Z will have
	        //same cordinate as circ1Z and changing along x in starting
	        // only and no change along z.
	        float slant1Z = circ1Z;
	        float slant2Z;
	        float slant3Z;
	        float slant4Z = circ1Z;
	        
	        //drawing upper hamisphere
	        int k;
	        
	        //traversing over stacks i.e longitudes
	        for(k = 0; k < stacks/2; k++){
	        	
	        	//taking radius of the new longitude
	        	//adding phi/2 to each phi as first longitude will be phi/2 above the
	        	//middle in odd case
	        	newRadius = (float)(radius*Math.cos((central_phi_angle*(k+1))+central_phi_angle/2));
	        	newY = (float)(radius*Math.sin((central_phi_angle*(k+1))+central_phi_angle/2));
	        	
	        	//drawing latitude
	        	for(int i = 0; i < slices; i++){
	        		
	        		//getting new circumference coordinates of latitude
	        		circ2X = (float)(crntRadius*Math.cos(central_theta_angle*(i+1)));
	        		circ2Z = (float)(crntRadius*Math.sin(central_theta_angle*(i+1)));


	        		//drawing curved surface
	        		slant1X = circ1X;
	        		slant1Z = circ1Z;
	        		slant2X = circ2X;
	        		slant2Z = circ2Z;
	        		
	        		//getting circumference coordinates of new next latidude
	        		slant3X = (float)((newRadius)*Math.cos(central_theta_angle*(i+1)));
	        		slant3Z = (float)((newRadius)*Math.sin(central_theta_angle*(i+1)));
	        		slant4X = (float)((newRadius)*Math.cos(central_theta_angle*(i)));
	        		slant4Z = (float)((newRadius)*Math.sin(central_theta_angle*(i)));

	        		addTriangle(slant2X,crntY,slant2Z, slant1X,crntY,slant1Z, slant4X,newY,slant4Z);
	        		addTriangle(slant2X,crntY,slant2Z, slant4X,newY,slant4Z, slant3X,newY,slant3Z);

	        		circ1X = circ2X;
	        		circ1Z = circ2Z;
	        	}
	        	circ1X = newRadius;
	        	circ1Z = 0;
	        	crntY = newY;
	        	crntRadius = newRadius;
	        }   
	        
	        //drawing lower hemisphere
	       
	        crntRadius = (float)(radius*Math.cos(-central_phi_angle/2));
	        circ1X = crntRadius;
	        circ1Z = 0;
	        crntY = (float)(radius*Math.sin(-central_phi_angle/2));
	        slant1X = circ1X;
	        slant1Z = circ1Z;
	        
	        for(k = 0; k < stacks/2; k++){
	        	newRadius = (float)(radius*Math.cos(-((central_phi_angle*(k+1))+central_phi_angle/2)));
	        	newY = (float)(radius*Math.sin(-((central_phi_angle*(k+1))+central_phi_angle/2)));
	        	for(int i = 0; i < slices; i++){
	        		
	        		circ2X = (float)(crntRadius*Math.cos(central_theta_angle*(i+1)));
	        		circ2Z = (float)(crntRadius*Math.sin(central_theta_angle*(i+1)));


	        		//drawing curved surface
	        		slant1X = circ1X;
	        		slant1Z = circ1Z;
	        		slant2X = circ2X;
	        		slant2Z = circ2Z;

	        		slant3X = (float)((newRadius)*Math.cos(central_theta_angle*(i+1)));
	        		slant3Z = (float)((newRadius)*Math.sin(central_theta_angle*(i+1)));
	        		slant4X = (float)((newRadius)*Math.cos(central_theta_angle*(i)));
	        		slant4Z = (float)((newRadius)*Math.sin(central_theta_angle*(i)));

	        		addTriangle(slant1X,crntY,slant1Z, slant3X,newY,slant3Z, slant4X,newY,slant4Z);
	        		addTriangle(slant1X,crntY,slant1Z, slant2X,crntY,slant2Z, slant3X,newY,slant3Z);

	        		circ1X = circ2X;
	        		circ1Z = circ2Z;
	        	}
	        	circ1X = newRadius;
	        	circ1Z = 0;
	        	crntY = newY;
	        	crntRadius = newRadius;
	        }
	        
	        //fill the middle rim;
	        crntRadius = (float)(radius*Math.cos(-central_phi_angle/2));
	        newRadius = (float)(radius*Math.cos(central_phi_angle/2));
	        circ1X = crntRadius;
	        circ1Z = 0;
	        crntY = (float)(radius*Math.sin(-central_phi_angle/2));
	        newY = (float)(radius*Math.sin(central_phi_angle/2));
	        slant1X = circ1X;
	        slant1Z = circ1Z;
	        
	    	for(int i = 0; i < slices; i++){
        		
        		circ2X = (float)(crntRadius*Math.cos(central_theta_angle*(i+1)));
        		circ2Z = (float)(crntRadius*Math.sin(central_theta_angle*(i+1)));


        		//drawing curved surface
        		slant1X = circ1X;
        		slant1Z = circ1Z;
        		slant2X = circ2X;
        		slant2Z = circ2Z;

        		slant3X = (float)((newRadius)*Math.cos(central_theta_angle*(i+1)));
        		slant3Z = (float)((newRadius)*Math.sin(central_theta_angle*(i+1)));
        		slant4X = (float)((newRadius)*Math.cos(central_theta_angle*(i)));
        		slant4Z = (float)((newRadius)*Math.sin(central_theta_angle*(i)));

        		addTriangle(slant2X,crntY,slant2Z, slant1X,crntY,slant1Z, slant4X,newY,slant4Z);
        		addTriangle(slant2X,crntY,slant2Z, slant4X,newY,slant4Z, slant3X,newY,slant3Z);

        		circ1X = circ2X;
        		circ1Z = circ2Z;
        	}
	}

	
	//same comments goes for this method as makeSphereOddStacks();
	//except here middle is not being drawn separatly.
	private void makeSphereEvenStacks(float radius, int slices, int stacks) {
		// TODO Auto-generated method stub
		 final double fullAngle = 360;
	        final double halfAngle = 180;
	        final double PI = 3.14159265f;
	        double central_theta_angle = (fullAngle/slices)*(PI/180); // in radians
	        double central_phi_angle = (halfAngle/stacks)*(PI/180); // in radians
	        
	        float y;
	        // circumference coordinates
	        
	        float circ1X;
	        float circ1Z;
	        
	        float circ2X;
	        float circ2Z;
	        

	        circ1X = 0.5f;
	        circ1Z = 0;
	        float crntRadius = radius;
	        float newRadius ;
	        float crntY = 0;
	        float newY;
	        //these slant coordinates are used to
	        //draw curved surface of cone. these are
	        //X coordinates of each four sided polygon
	        //divided in two triangles and 1,2,3,4 denotes
	        //X coordinates taken in clockwise order
	        //where slant1X denotes bottom right X cordinate
	        //of each four sided polygon.
	        //in starting slant1X = circ1X and 
	        //slant2X = circ2X and will be determined , slant3X will determined
	        //in the loop using circ2X and slant4X is 
	        //radiusDivLen minus circ1X in starting.
	        
	        float slant1X = circ1X;
	        float slant2X;
	        float slant3X;
	        float slant4X;
	        
	        //same explanation as for slantX , only slant4Z will have
	        //same cordinate as circ1Z and changing along x in starting
	        // only and no change along z.
	        float slant1Z = circ1Z;
	        float slant2Z;
	        float slant3Z;
	        float slant4Z = circ1Z;
	        
	        //drawing upper hamisphere
	        int k;
	        for(k = 0; k < stacks/2; k++){
	        	newRadius = (float)(radius*Math.cos(central_phi_angle*(k+1)));
	        	newY = (float)(radius*Math.sin(central_phi_angle*(k+1)));
	        	for(int i = 0; i < slices; i++){
	        		
	        		circ2X = (float)(crntRadius*Math.cos(central_theta_angle*(i+1)));
	        		circ2Z = (float)(crntRadius*Math.sin(central_theta_angle*(i+1)));


	        		//drawing curved surface
	        		slant1X = circ1X;
	        		slant1Z = circ1Z;
	        		slant2X = circ2X;
	        		slant2Z = circ2Z;

	        		slant3X = (float)((newRadius)*Math.cos(central_theta_angle*(i+1)));
	        		slant3Z = (float)((newRadius)*Math.sin(central_theta_angle*(i+1)));
	        		slant4X = (float)((newRadius)*Math.cos(central_theta_angle*(i)));
	        		slant4Z = (float)((newRadius)*Math.sin(central_theta_angle*(i)));

	        		addTriangle(slant2X,crntY,slant2Z, slant1X,crntY,slant1Z, slant4X,newY,slant4Z);
	        		addTriangle(slant2X,crntY,slant2Z, slant4X,newY,slant4Z, slant3X,newY,slant3Z);

	        		circ1X = circ2X;
	        		circ1Z = circ2Z;
	        	}
	        	circ1X = newRadius;
	        	circ1Z = 0;
	        	crntY = newY;
	        	crntRadius = newRadius;
	        }   
	        //drawing lower hemisphere
	        circ1X = radius;
	        circ1Z = 0;
	        crntRadius = radius;
	        crntY = 0;
	        slant1X = circ1X;
	        slant1Z = circ1Z;
	        
	        for(k = 0; k < stacks/2; k++){
	        	newRadius = (float)(radius*Math.cos(-central_phi_angle*(k+1)));
	        	newY = (float)(radius*Math.sin(-central_phi_angle*(k+1)));
	        	for(int i = 0; i < slices; i++){
	        		
	        		circ2X = (float)(crntRadius*Math.cos(central_theta_angle*(i+1)));
	        		circ2Z = (float)(crntRadius*Math.sin(central_theta_angle*(i+1)));


	        		//drawing curved surface
	        		slant1X = circ1X;
	        		slant1Z = circ1Z;
	        		slant2X = circ2X;
	        		slant2Z = circ2Z;

	        		slant3X = (float)((newRadius)*Math.cos(central_theta_angle*(i+1)));
	        		slant3Z = (float)((newRadius)*Math.sin(central_theta_angle*(i+1)));
	        		slant4X = (float)((newRadius)*Math.cos(central_theta_angle*(i)));
	        		slant4Z = (float)((newRadius)*Math.sin(central_theta_angle*(i)));

	        		addTriangle(slant1X,crntY,slant1Z, slant3X,newY,slant3Z, slant4X,newY,slant4Z);
	        		addTriangle(slant1X,crntY,slant1Z, slant2X,crntY,slant2Z, slant3X,newY,slant3Z);

	        		circ1X = circ2X;
	        		circ1Z = circ2Z;
	        	}
	        	circ1X = newRadius;
	        	circ1Z = 0;
	        	crntY = newY;
	        	crntRadius = newRadius;
	        }
	}
    
   
}

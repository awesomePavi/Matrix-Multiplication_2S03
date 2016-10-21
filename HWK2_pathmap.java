/*
Name: [Pavithran Pathmarajah]
MacID: [pathmap]
Student Number: [1410729]
Description: [Takes an input of Matrices, Multiplies them and finds their inverse]
Bonus: [Iterative]
*/

public class HWK2_pathmap {
	public static void main(String args[]){
		
	/*~~~~~~~~~~~~~~~~~~INPUT TO MATRICES~~~~~~~~~~~~~~~~~~~~*/
	//Store total number of matrices to collect information for
	int numArrays = Integer.parseInt(args[0]); 
	//to store size of matrices
	int[] values = new int[numArrays*2]; 
	for (int i=1; i<=(numArrays*2);i++){
		//store matrix info, iterate by 2 because x and y is stored
		values[i-1]=Integer.parseInt(args[i]);
	}
	
	// Create 3D array for Matrices
	float[][][] matrices = new float[numArrays][1][1];
	int pos = numArrays*2; // Location in input string
	
	//keep track of current Matrix
	for (int i=0; i<numArrays*2;i+=2){
		//Create Matrix and transfer value
		float[][] tmp = new float[values[i]][values[i+1]]; //using tmp because accessing an array within an array is time consuming
		// fill array with data row by row
		for (int y =0; y<values[i];y++){
			for (int x = 0; x<values[i+1];x++){
				//keep track of location in input string
				pos+=1;
				tmp[y][x]=Integer.parseInt(args[pos]);
			}
		}
		//Store in 3D array
		matrices[i/2]=tmp; 
	}
	
	/*~~~~~~~~~~~~~~~~~~ Multiply ~~~~~~~~~~~~~~~~~~~~*/
	//Check Multiplicity
	//In and A x B scenario the columns of A must equal the rows of B
	int tmpx = matrices[0][0].length; //Set length to columns of A
	boolean cont=true; //To determine if it can be multiplied or not
	//check all matrices
	for (int i=1;i<matrices.length;i++){
		//If columns of A equals rows of B then set B as the new A and continue to the next operation
		if (tmpx == matrices[i].length ){
			tmpx = matrices[i][0].length;
		// Columns of A do not equal rows of B cannot multiply
		}else{
			cont = false;
			break;
		}
	}
	
	
	// If multiplication can proceed
	if (cont){
		
			// Create final Matrix, set it to first matrix is treated as A in A x B equation
			float[][] FinalMatrix = matrices[0];
			// go through all matrices, i=1 because 0 is set as A and i represents B in A x B equation
			for (int i=1;i<matrices.length;i++){
				//temporary values used for calculations
				float[][] tmp1 = FinalMatrix;
				float[][] tmp2 = matrices[i];
				// Takes rows from A Columns from B
				FinalMatrix = new float[tmp1.length][tmp2[0].length];
				//Go through all locations in product array
				for (int x=0; x<FinalMatrix[0].length;x++){
					for (int y=0;y<FinalMatrix.length;y++){
						int tmpVal=0;
						// Go through Column in A and row in B and the sum of their products is the value in that row column location
						for (int v=0;v<tmp1[0].length;v++){
							tmpVal+=tmp1[y][v]*tmp2[v][x];
						}
						// store in final matrix
						FinalMatrix[y][x]=tmpVal;
					}
				}
			}	
			
			int dimensions=FinalMatrix.length; // save time instead of calling array length function all the time
			
			/*~~~~~~~~~~~~~~~~~~ Gauss Jordan Elimination For Inverse ~~~~~~~~~~~~~~~~~~~~*/
			// Identity matrix to be converted into Inverted Matrix
			float[][] Identity = new float[dimensions][dimensions];
			// set values of Identity Matrix
			for (int i=0;i<dimensions;i++){
				Identity[i][i]=1;
			}
			//To solve , must make original matrix into an identity matrix which means from top 
			//left to bottom right all ones, this represents those locations
			for (int Pos=0;Pos<dimensions;Pos++){
				//Set Pivot at point, set this position ex. (1,1) to 1 then use it to make rest of column 0
				float divBy=FinalMatrix[Pos][Pos]; //value of division required to make (n,n) position 1
				// Divide entire row by calculated value
				for (int x=0;x<dimensions;x++){
					FinalMatrix[Pos][x]=FinalMatrix[Pos][x]/divBy;
					Identity[Pos][x]=Identity[Pos][x]/divBy;
				}
				//Eliminate all values in Column
				for(int y=0;y<dimensions; y++){
					//Skip current pivot point, pivot points must be 1
					//if not a pivot point and not already equal to 0 at this position
					if (y!=Pos && FinalMatrix[y][Pos]!=0.0){ 
						//since pivot is 1, we can take value in this position and multiply it by
						//the pivot and subtract it from the row, it wont affect existing pivots because
						//everything to the left of the pivot is 0
						divBy=FinalMatrix[y][Pos];
						//subtract value from row
						for (int x=0;x<dimensions;x++){
							// Apply operation to initial matrix and identity matrix
							FinalMatrix[y][x]-=FinalMatrix[Pos][x]*divBy;
							Identity[y][x]-=Identity[Pos][x]*divBy;
						}
					}
				}
			}
			
			//Test If GJE done correctly, if not matrix is not-invertible
			boolean correct=true;
			// check the diagonal is 1 and all else is 0
			for (int x=0;x<dimensions;x++){
				for(int y=0;y<dimensions;y++){
					// if on diagonal
					if (x==y){
					correct=FinalMatrix[y][x]==1.0;
					//if not on diagonal
					}else{
						correct=FinalMatrix[y][x]==0.0;	
					}
				}
			}
			
			// if successfully inverted
			if (correct){
				// Disaply matrix values row by row
				for(int x=0; x<FinalMatrix.length;x++){
					for(int y=0; y<FinalMatrix[x].length;y++){
						System.out.print(Identity[x][y]+"   ");
					}
				}	
			// if failed inversion test
			}else{
				System.out.println("Matrix not invertible");
			}
	// if failed multiplicity test
	}else{
		System.out.println("Multiplication error");
	}
	
}	
}
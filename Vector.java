/**
 *A basic implementation of the mathematical vector
 *Supports scaling, normalization, angles, dot products, cross products (3 dimensions only), and parallel/perpendicular checking
 *Immutable class
 */
public class Vector{

  //instance variables
  private double[] coordinates;
  private double magnitude;
  private int dimension;

  //Constructors

  //Default constructor, a 0 vector
  public Vector(){
    coordinates = new double[0];
    magnitude = 0;
    setDimension();
  }

  //Creates a vector with a series of double inputs
  public Vector(double... a){
    if(a.length == 1)
      throw new IllegalArgumentException("A vector must have at least two dimensions.");
    coordinates = new double[a.length];
    for(int i = 0; i < a.length; i++)
      coordinates[i] = a[i];
    setMagnitude();
    setDimension();
  }

  //Creates a vector with an input of a double[]
 /* public Vector(double[] input){
    if(input.length <= 1)
      throw new IllegalArgumentException("A vector must have at least two dimensions.");
    coordinates = new double[input.length];
    for(int i = 0; i < input.length; i++){
      coordinates[i] = input[i];
    }
    setMagnitude();
    setDimension();
  }*/

  //Given a Vector as an input, creates a copy of that Vector
  public Vector(Vector input){
    this(input.coordinates);
  }

//Instance methods

  //Private methods

 /**
  *Calculates the magnitude of the current vector
  *@return The magnitude of the vector as a double
  */
  private double calculateMagnitude(){
    double sum = 0;
    for(double x : coordinates)
      sum += Math.pow(x, 2);
    return Math.sqrt(sum);
  }

 /**
  *Sets the magnitude of the current vector object
  */
  private void setMagnitude(){
    magnitude = calculateMagnitude();
  }

 /**
  *Sets the dimension of the vector by the number of its coordinates
  */
  private void setDimension(){
    dimension = coordinates.length;
  }

//Public methods
  
  //Calculator methods, operations with other vectors

 /**
  *Returns the dot product of this vector and another vector
  *@param other Another vector object which must be of the same dimension
  *@return a double representing the dot product
  */
  public double dotProduct(Vector other){
    if(this.dimension != other.dimension)
      throw new IllegalArgumentException("The vectors must be of the same dimension.");
    double output = 0;
    for(int i = 0; i < this.coordinates.length; i++){
      output += this.coordinates[i] * other.coordinates[i];
    }
    return output;
  }

 /**
  *Returns the cross product of this vector and another vector
  *Note that cross products only exist in the 3rd and 7th dimension
  *This method only implements the 3rd dimensional cross product
  *@param other The other vector we are crossing
  *@return a vector that is the cross product of this and toher
  */
  public Vector crossProduct(Vector other){
    if(this.dimension != other.dimension)
      throw new IllegalArgumentException("The vectors must be of the same dimension.");
    if(this.dimension != 3 || other.dimension != 3)
      throw new IllegalArgumentException("Cross products only exist for vectors in the 3rd (and 7th) dimension.");

    double a = this.coordinates[1] * other.coordinates[2] - this.coordinates[2] * other.coordinates[1];
    double b = -1 * this.coordinates[0] * other.coordinates[2] - this.coordinates[2] * other.coordinates[0];
    double c = this.coordinates[0] * other.coordinates[1] - this.coordinates[1] * other.coordinates[0];
    return new Vector(a, b, c);
  }

 /**
  *Returns a normalized version of this vector
  *@return a Vector with magnitude 1 but still in the same direction
  */
  public Vector normalize(){
    double[] newCoordinates = new double[coordinates.length];
    for(int i = 0; i < newCoordinates.length; i++){
      newCoordinates[i] = coordinates[i] / magnitude;
    }
    return new Vector(newCoordinates);
  }

 /**
  *Returns this vector scaled by n
  *@return a Vector with coordinates multiplied by n
  */
  public Vector scale(double n){
    double[] newCoordinates = new double[coordinates.length];
    for(int i = 0; i < coordinates.length; i ++){
      newCoordinates[i] = coordinates[i] * n;
    }
    return new Vector(newCoordinates);
  }

 /**
  *Returns the angle between this vector and another vector in radians
  *@param other The other vector
  *@return the angle between this vector and the other vector using the dot product formula in radians
  */
  public double angle(Vector other){
    double dotProduct = dotProduct(other);
    double magnitudes = this.magnitude * other.magnitude;
    return Math.acos(dotProduct / magnitudes);
  }

  /**
   *Returns the angle between this vector and another vector in degrees
   *@param other The other vector
   *@return the angle between this vector and the other vector using the dot product formula in degrees
   */
  public double angleDegrees(Vector other){
    return Math.toDegrees(angle(other));
  }

 /**
  *Checks if this vector is parallel to another vector
  *@param other The other vector
  *@return true if the angle is 0, false if not (within a millionth of a radian)
  */
  public boolean isParallel(Vector other){
    return angle(other) <= 0.000001;
  }

 /**
  *Checks if this vector is perpendicular to another vecotr
  *@param other The other vector
  *@return true if the dot product is 1, false if not (within a millionth)
  */
  public boolean isPerpendicular(Vector other){
    return dotProduct(other) - 1 <= 0.000001;
  }

  //Getter methods

 /**
  *Gives the magnitude of the current vector
  *@return instance variable magnitude
  */
  public double getMagnitude(){
    return magnitude;
  }

 /**
  *Returns a copy of this vector's coordinates
  *@return a double[] copy of this vector's coordinates variable
  */
  public double[] getCoordinates(){
    double[] output = new double[coordinates.length];
    for(int i = 0; i < coordinates.length; i++){
      output[i] = coordinates[i];
    }
    return output;
  }

 /**
  *Returns the dimension of the vector
  *@return dimension
  */
  public int getDimension(){
    return dimension;
  }
}

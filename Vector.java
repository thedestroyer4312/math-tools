/**
 *A basic implementation of the mathematical vector
 *Supports scaling, normalization, angles, dot products, cross products (3 dimensions only), and parallel/perpendicular checking
 *Immutable class
 *@author Trevor Tsai
 */
public class Vector{

  //instance variables
  private double[] coordinates;
  private double magnitude;
  private int dimension;

  //Constructors

  //Default constructor, a 0 Vector in 3 dimensions
  public Vector(){
    coordinates = new double[3];
    setMagnitude();
    setDimension();
  }

  //Creates an n-dimensional Vector with a series of double inputs
  public Vector(double... a){
    if(a.length <= 1)
      throw new IllegalArgumentException("A vector must have at least two dimensions.");
    coordinates = new double[a.length];
    for(int i = 0; i < a.length; i++)
      coordinates[i] = a[i];
    setMagnitude();
    setDimension();
  }

  //Given a Vector and a desired magnitude n, creates a Vector in the same direction with magnitude n
  public Vector(Vector input, double n){
    this(input.normalize().scale(n));
  }

  //Given a Vector as an input, creates a copy of that Vector
  public Vector(Vector input){
    this(input.coordinates);
  }

//Instance methods

  //Private methods

 /**
  *Calculates the magnitude of the current Vector
  *@return The magnitude of the Vector as a double
  */
  private double calculateMagnitude(){
    double sum = 0;
    for(double x : coordinates)
      sum += Math.pow(x, 2);
    return Math.sqrt(sum);
  }

 /**
  *Sets the magnitude of the current Vector object
  */
  private void setMagnitude(){
    magnitude = calculateMagnitude();
  }

 /**
  *Sets the dimension of the Vector by the number of its coordinates
  */
  private void setDimension(){
    dimension = coordinates.length;
  }

//Public methods
  
  //Calculator methods, operations with other vectors

 /**
  *Returns a Vector that is the sum of this Vector and another Vector
  *@param other The other vector
  *@return a new Vector that is the sum of these two vectors
  */
  public Vector add(Vector other){
    if(this.dimension != other.dimension)
      throw new IllegalArgumentException("The vectors must be of the same dimension.");
    double[] newCoords = new double[this.coordinates.length];
    for(int i = 0; i < this.coordinates.length; i++){
      newCoords[i] = this.coordinates[i] + other.coordinates[i];
    }
    return new Vector(newCoords);
  }
 
 /**
  *Returns a Vector that is the difference between this Vector and another Vector
  *Note that a - b in vectors is equivalent to a + (-b)
  *@param other The other Vector
  *@return a new Vector that is the difference of these two vectors (the sum of this vector and the negative of the other Vector)
  */
  public Vector subtract(Vector other){
    if(this.dimension != other.dimension)
      throw new IllegalArgumentException("The vectors must be of the same dimension.");
    double[] newCoords = new double[this.coordinates.length];
    for(int i = 0; i < this.coordinates.length; i++){
      newCoords[i] = this.coordinates[i] - other.coordinates[i];
    }
    return new Vector(newCoords);
  }

 /**
  *Returns the dot product of this Vector and another Vector
  *@param other Another Vector object which must be of the same dimension
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
  *Returns the cross product of this Vector and another Vector
  *Note that cross products only exist in the 3rd and 7th dimension
  *This method only implements the 3rd dimensional cross product
  *@param other The other Vector we are crossing
  *@return a Vector that is the cross product of this and toher
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
  *Returns the projection of this Vector onto the Vector u
  *@param u The other Vector onto which we are projecting this Vector
  *@return a new Vector that is the projection of this Vector onto u
  */
  public Vector projection(Vector u){
    if(this.dimension != u.dimension)
      throw new IllegalArgumentException("The vectors must be of the same dimension.");
    double dotProduct = dotProduct(u);
    Vector output = new Vector(u.normalize());
    return output.scale(dotProduct / u.magnitude);
  }

 /**
  *Returns the component of this Vector onto the Vector u
  *Note that the component would be the magnitude of the projection of this Vector onto u
  *@param u The other Vector
  *@return a double that represents the component, the magnitude of the projection
  */
  public double component(Vector u){
    return projection(u).magnitude;
  }

 /**
  *Returns a normalized version of this Vector
  *@return a Vector with magnitude 1 in the same direction
  */
  public Vector normalize(){
    double[] newCoordinates = new double[coordinates.length];
    for(int i = 0; i < newCoordinates.length; i++){
      newCoordinates[i] = coordinates[i] / magnitude;
    }
    return new Vector(newCoordinates);
  }

 /**
  *Returns this Vector scaled by n
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
  *Returns the angle between this Vector and another Vector in radians
  *@param other The other Vector
  *@return the angle between this Vector and the other Vector using the dot product formula in radians
  */
  public double angle(Vector other){
    double dotProduct = dotProduct(other);
    double magnitudes = this.magnitude * other.magnitude;
    return Math.acos(dotProduct / magnitudes);
  }

  /**
   *Returns the angle between this Vector and another Vector in degrees
   *@param other The other Vector
   *@return the angle between this Vector and the other Vector using the dot product formula in degrees
   */
  public double angleDegrees(Vector other){
    return Math.toDegrees(angle(other));
  }

 /**
  *Checks if this Vector is parallel to another Vector
  *@param other The other Vector
  *@return true if the difference between product of magnitudes and dot product is 0, false if not (within a millionth of a radian)
  */
  public boolean isParallel(Vector other){
    return Math.abs(dotProduct(other) - (this.magnitude * other.magnitude)) <= 0.000001;
  }

 /**
  *Checks if this vector is perpendicular to another Vector
  *@param other The other Vector
  *@return true if the dot product is 0, false if not (within a millionth)
  */
  public boolean isPerpendicular(Vector other){
    return Math.abs(dotProduct(other))  <= 0.000001;
  }

  //Getter methods

 /**
  *Gives the magnitude of the current Vector
  *@return instance variable magnitude
  */
  public double getMagnitude(){
    return magnitude;
  }

 /**
  *Returns a copy of this Vector's coordinates
  *@return a double[] copy of this Vector's coordinates variable
  */
  public double[] getCoordinates(){
    double[] output = new double[coordinates.length];
    for(int i = 0; i < coordinates.length; i++){
      output[i] = coordinates[i];
    }
    return output;
  }

 /**
  *Returns the dimension of the Vector
  *@return dimension
  */
  public int getDimension(){
    return dimension;
  }
}

package sg.edu.np.mad.happyhabit;

public class Food {

    private int foodNo;

    private String foodName;

    private String foodCat;

    private int foodServing;

    private int foodCalorie;

    public Food(){}

    public Food(int foodNo, String foodName, String foodCat, int foodServing, int foodCalorie){
        this.foodNo = foodNo;
        this.foodName = foodName;
        this.foodCat = foodCat;
        this.foodServing = foodServing;
        this.foodCalorie = foodCalorie;
    }


    public int getFoodNo() {
        return foodNo;
    }

    public void setFoodNo(int foodNo) {
        this.foodNo = foodNo;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCat() {
        return foodCat;
    }

    public void setFoodCat(String foodCat) {
        this.foodCat = foodCat;
    }

    public int getFoodServing() {
        return foodServing;
    }

    public void setFoodServing(int foodServing) {
        this.foodServing = foodServing;
    }

    public int getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(int foodCalorie) {
        this.foodCalorie = foodCalorie;
    }
}

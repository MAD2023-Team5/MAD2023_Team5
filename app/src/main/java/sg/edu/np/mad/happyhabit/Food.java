package sg.edu.np.mad.happyhabit;

public class Food {

    int foodCalorie;
    String foodCat;
    String foodName;
    int foodNo;
    int foodServing;
    boolean isSelected;

    public Food() {
    }

    public Food(int foodCalorie, String foodCat, String foodName, int foodNo, int foodServing, boolean isSelected) {
        this.foodCalorie = foodCalorie;
        this.foodCat = foodCat;
        this.foodName = foodName;
        this.foodNo = foodNo;
        this.foodServing = foodServing;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getFoodCalorie() {
        return foodCalorie;
    }

    public void setFoodCalorie(int foodCalorie) {
        this.foodCalorie = foodCalorie;
    }

    public String getFoodCat() {
        return foodCat;
    }

    public void setFoodCat(String foodCat) {
        this.foodCat = foodCat;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodNo() {
        return foodNo;
    }

    public void setFoodNo(int foodNo) {
        this.foodNo = foodNo;
    }

    public int getFoodServing() {
        return foodServing;
    }

    public void setFoodServing(int foodServing) {
        this.foodServing = foodServing;
    }
}

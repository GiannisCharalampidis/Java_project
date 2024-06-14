import java.util.List;

import javax.swing.table.DefaultTableModel;

public class CarListResult {
	private DefaultTableModel carList;
    private List<String> carModelsList;

    public CarListResult(DefaultTableModel carList, List<String> carModelsList) {
        this.carList = carList;
        this.carModelsList = carModelsList;
    }

    public DefaultTableModel getCarList() {
        return carList;
    }

    public List<String> getCarModelsList() {
        return carModelsList;
    }
}

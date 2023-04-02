package hu.bme.mit.train.interfaces;

public interface Tachograph {

	void recordData();

	com.google.common.collect.Table<java.util.Date, Integer, Integer> getData();

}

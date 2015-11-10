package com.fekrasoftware.androidtask.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamedzakaria on 11/7/15.
 */
@Table(name = "products")
public class Product extends Model implements Parcelable {
    @SerializedName("id")
    @Column(name = "product_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int product_id;
    @Column
    private Image image;
    @Column
    private String productDescription;
    @Column
    private int price;


    public Product() {
        super();
    }

    public Product(Parcel parcel){
        super();
        product_id = parcel.readInt();
        price = parcel.readInt();
        productDescription = parcel.readString();
        image = (Image) parcel.readParcelable(Image.class.getClassLoader());
    }

    public int getProduct_id() {
        return product_id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product_id);
        dest.writeInt(price);
        dest.writeString(productDescription);
        dest.writeParcelable(image, flags);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

}

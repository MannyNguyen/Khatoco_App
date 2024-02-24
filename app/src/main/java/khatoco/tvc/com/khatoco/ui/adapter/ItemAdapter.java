package khatoco.tvc.com.khatoco.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import khatoco.tvc.com.khatoco.R;
import khatoco.tvc.com.khatoco.ui.activity.MainActivity;
import khatoco.tvc.com.khatoco.ui.objects.ItemProduct;

/**
 * Created by Quysunam on 11/9/2016.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int PRODUCT = 1;
    public final int PROMOTION = 3;
    public final int TAGPROMOTION = 2;
    public MainActivity context;

    private List<ItemProduct> itemProductList;

    public ItemAdapter(List<ItemProduct> itemProductList, MainActivity context) {
        this.itemProductList = itemProductList;
        this.context = context;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtNumOfProduct, txtPriceProduct, txtProductPrice;
        Button imageButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
            txtNumOfProduct = (TextView) itemView.findViewById(R.id.txt_num_product);
            txtPriceProduct = (TextView) itemView.findViewById(R.id.txt_product_price);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txt_price_product);
            imageButton = (Button) itemView.findViewById(R.id.imageButton);
        }

        public void bindData(ItemProduct product, final int position) {
            txtProductName.setText(product.getProductName());
            txtNumOfProduct.setText(String.valueOf(product.getNumberProduct()));
            txtPriceProduct.setText(String.valueOf(product.getPriceProduct()));
            txtProductPrice.setText(String.valueOf(product.getPrice()));

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(position);
                }
            });
        }

    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName, txtNumOfProduct;
        Button imageButton;

        public PromotionViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
            txtNumOfProduct = (TextView) itemView.findViewById(R.id.txt_num_product);

        }

        public void bindData(ItemProduct product, final int position) {
            txtProductName.setText(product.getProductName());
            txtNumOfProduct.setText(String.valueOf(product.getNumberProduct()));
            imageButton = (Button) itemView.findViewById(R.id.imageButton);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(position);
                }
            });
        }

    }

    public class TagProductViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName, txtNumOfProduct, txtPriceProduct, txtProductPrice;
        Button imageButton;

        public TagProductViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
            txtNumOfProduct = (TextView) itemView.findViewById(R.id.txt_num_product);
            txtPriceProduct = (TextView) itemView.findViewById(R.id.txt_product_price);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txt_price_product);
            imageButton = (Button) itemView.findViewById(R.id.imageButton);
        }

        public void bindData(ItemProduct product, final int position) {
            txtProductName.setText(product.getProductName());
            txtNumOfProduct.setText(String.valueOf(product.getNumberProduct()));
            txtPriceProduct.setText(String.valueOf(product.getPriceProduct()));
            txtProductPrice.setText(String.valueOf(product.getPrice()));

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(position);
                }
            });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == PRODUCT) {
            return new ItemAdapter.ProductViewHolder(inflater.inflate(R.layout.item_product, parent, false));
        } else if (viewType == PROMOTION) {
            return new ItemAdapter.PromotionViewHolder(inflater.inflate(R.layout.item_promotion, parent, false));
        } else {
            return new ItemAdapter.TagProductViewHolder(inflater.inflate(R.layout.item_tag_product, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemProduct item = itemProductList.get(position);
        if (getItemViewType(position) == PRODUCT) {
            ((ItemAdapter.ProductViewHolder) holder).bindData(item, position);
        }

        if (getItemViewType(position) == PROMOTION) {
            ((ItemAdapter.PromotionViewHolder) holder).bindData(item, position);
        }

        if (getItemViewType(position) == TAGPROMOTION) {
            ((ItemAdapter.TagProductViewHolder) holder).bindData(item, position);
        }


    }

    @Override
    public int getItemViewType(int position) {
        ItemProduct item = itemProductList.get(position);
        if (item.getType() == 1) {
            return PRODUCT;
        } else if (item.getType() == 3) {
            return PROMOTION;
        } else {
            return TAGPROMOTION;
        }
    }

    @Override
    public int getItemCount() {
        return itemProductList.size();
//        return null;
    }


//    Activity context;
//    int resource;
//
//    public ItemAdapter(Activity context, int resource) {
//        super(context, resource);
//        this.context = context;
//        this.resource = resource;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = this.context.getLayoutInflater().inflate(this.resource, null);
//        TextView txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
//        TextView txtProductType = (TextView) view.findViewById(R.id.txt_product_type);
//        TextView txtNumOfProduct = (TextView) view.findViewById(R.id.txt_num_product);
//        TextView txtPriceProduct = (TextView) view.findViewById(R.id.txt_product_price);
//        ItemProduct item = getItem(position);
//
//        txtProductName.setText(item.getProductName());
//        txtProductType.setText(item.getProductType());
//        txtNumOfProduct.setText(item.getNumberProduct());
//        txtPriceProduct.setText(item.getPriceProduct());
//
//        return view;
//    }

    private void removeAt(int position) {
        if(itemProductList.get(position).getType() == 1){
            context.caculateTotalMount(itemProductList.get(position));
        }
        itemProductList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemProductList.size());
    }
}

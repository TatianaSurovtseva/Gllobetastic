package chaosinc.com.gllobetastic.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chaosinc.com.gllobetastic.R;


/**
 * List item adapter class
 */
public class PictureListItemAdapter extends RecyclerView.Adapter<PictureListItemAdapter.ViewHolder> {

    final Context context;
    private final List<Picture> pictureArrayList = DatabaseConnection.getInstance().getArrayList();

    public PictureListItemAdapter(Context context, List<Picture> pictureArrayList) {
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_picture_item, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.populateRow(getItem(position));
    }

    @Override
    public int getItemCount() {
        return pictureArrayList.size();
    }

    private Picture getItem(int position) {
        return pictureArrayList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private final TextView pictureName;
        private final ImageView pictureSelf;

        public ViewHolder(View view) {
            super(view);
            pictureName = view.findViewById(R.id.picureNameTextView);
            pictureSelf = view.findViewById(R.id.picture_image_view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //nothing happens on click
        }

        /**
         * @param picture populates the rows with pictures
         */
        public void populateRow(Picture picture) {

            if (pictureArrayList == null) {
                //do nothing
            } else {
                pictureName.setText(picture.getCity());
                pictureSelf.setImageBitmap(BitmapHelper.convert(picture.getPictureBitmap()));

            }
        }
    }
}

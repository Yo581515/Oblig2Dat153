package com.example.oblig2dat153.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.Utils.ConverterHelper;
import com.example.oblig2dat153.databinding.InsertImageFragmentWindowBinding;
import com.example.oblig2dat153.model.Image;

import java.io.IOException;

public class InsertImageFragment extends AppCompatDialogFragment {

    // addClickListner
    OnAddClickListener onAddClickListener;

    // Binder
    InsertImageFragmentWindowBinding insertImageFragmentWindowBinding;

    //data
    Image image;

    // class Fraagment click hander
    FragmentClickHandler fragmentClickHandler;

    // constructor
    public InsertImageFragment() {
        image = new Image();

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_image_search_24);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        byte[] byteImage = ConverterHelper.BitmapToByteArray(bitmap);
        image.setImageData(byteImage);

        // binding tools
        // 1) initieate binder
        insertImageFragmentWindowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.insert_image_fragment_window,
                null,
                false
        );

        // 2) set data and set clickhandlers
        insertImageFragmentWindowBinding.setImage(image);
        fragmentClickHandler = new FragmentClickHandler();
        insertImageFragmentWindowBinding.setClickHandler(fragmentClickHandler);

        // pop up dialog
        View view = insertImageFragmentWindowBinding.getRoot();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);





        builder
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (TextUtils.isEmpty(image.getImageName())) {
                            Toast.makeText(getActivity().getApplication(), "Please Enter a Name", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            getDialog().dismiss();
                        }
                        onAddClickListener.onAddClick(image);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    public interface OnAddClickListener {
        void onAddClick(Image image);
    }

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }

    public class FragmentClickHandler {
        public void onImageClick(View view) {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            launcher.launch(intent);
        }
    }

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), imageUri);
                        Bitmap bitmapImage2 = ImageDecoder.decodeBitmap(source);
                        byte[] byteImage = ConverterHelper.BitmapToByteArray(bitmapImage2);
                        image.setImageData(byteImage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    );
}

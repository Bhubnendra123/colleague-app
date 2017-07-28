package com.good.gd.example.skeleton;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.good.gd.GDAndroid;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.auth.AuthScope;
import com.good.gd.apache.http.auth.NTCredentials;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.net.GDHttpClient;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by developer01 on 12/07/2017.
 */
public class ColleagueManagerDetailsActivity extends Activity {

    private RelativeLayout nameContainer;
    private RelativeLayout mobileContainer;
    private RelativeLayout alternateContactContainer;
    private RelativeLayout homePhoneContainer;
    private RelativeLayout internalLandlineContainer;
    private RelativeLayout landlineContainer;
    private RelativeLayout postalAddressContainer;
    private RelativeLayout emailAddressContainer;
    private RelativeLayout lineManagerContainer;
    private RelativeLayout secretaryContainer;
    private RelativeLayout jobTitleContainer;
    private RelativeLayout departmentContainer;
    private RelativeLayout businessUnitContainer;
    private RelativeLayout divisionContainer;
    private RelativeLayout userIdContainer;
    private RelativeLayout countryContainer;
    private RelativeLayout costCentreContainer;
    private RelativeLayout websiteContainer;
    private RelativeLayout addtnlLocationContainer;
    private RelativeLayout axisGroupContainer;
    private RelativeLayout mwotGroupContainer;
    private RelativeLayout imageContainer;


    private String hostName;
    private ProgressBar progressBar;
    private TextView name;
    private TextView title;
    private TextView mobileNumber;
    private TextView altContact;
    private TextView homePhone;
    private TextView internalLandline;
    private TextView landline;
    private TextView postalAddress;
    private TextView emailAddress;
    private TextView lineManager;
    private TextView secretary;
    private TextView jobTitle;
    private TextView department;
    private TextView businessUnit;
    private TextView division;
    private TextView userID;
    private TextView country;
    private TextView costCentre;
    private TextView website;
    private TextView addtnlLocation;
    private TextView axisGroup;
    private TextView mwotGroup;
    private ImageView image;
    private TouchHighlightImageButton profileImage;
    private Skeleton skeleton;
    private Bitmap bitmap;
    private Animator mCurrentAnimator;
    private View mLayout;

    private static final int REQUEST_CALL = 0;

    private int permissionCheck = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GDAndroid.getInstance().activityInit(this);
        setContentView(R.layout.activity_manager_details);
        mLayout = findViewById(R.id.main_layout);

        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setIcon(R.drawable.back_chevron);
        setTitle("Colleague Details");

        final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");
        findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        skeleton = new Skeleton();

        initialize();

        Intent intent = getIntent();
        Colleague colleague = intent.getExtras().getParcelable("colleague_details");
        bitmap = (Bitmap) intent.getParcelableExtra("Image");
        setData(colleague, bitmap);
    }


    private void initialize() {
        nameContainer = (RelativeLayout) findViewById(R.id.name_container);
        mobileContainer = (RelativeLayout) findViewById(R.id.mobile_number_container);
        alternateContactContainer = (RelativeLayout) findViewById(R.id.alt_number_container);
        homePhoneContainer = (RelativeLayout) findViewById(R.id.home_phone_container);
        internalLandlineContainer = (RelativeLayout) findViewById(R.id.internal_landline_container);
        landlineContainer = (RelativeLayout) findViewById(R.id.landline_container);
        imageContainer = (RelativeLayout) findViewById(R.id.image_container);
        postalAddressContainer = (RelativeLayout) findViewById(R.id.postal_address_container);
        emailAddressContainer = (RelativeLayout) findViewById(R.id.email_address_container);
        lineManagerContainer = (RelativeLayout) findViewById(R.id.line_manager_container);
        secretaryContainer = (RelativeLayout) findViewById(R.id.secretary_container);
        jobTitleContainer = (RelativeLayout) findViewById(R.id.job_title_container);
        departmentContainer = (RelativeLayout) findViewById(R.id.department_container);
        businessUnitContainer = (RelativeLayout) findViewById(R.id.business_unit_container);
        divisionContainer = (RelativeLayout) findViewById(R.id.division_container);
        userIdContainer = (RelativeLayout) findViewById(R.id.user_id_container);
        countryContainer = (RelativeLayout) findViewById(R.id.country_container);
        costCentreContainer = (RelativeLayout) findViewById(R.id.cost_centre_container);
        websiteContainer = (RelativeLayout) findViewById(R.id.website_container);
        addtnlLocationContainer = (RelativeLayout) findViewById(R.id.additional_location_container);
        axisGroupContainer = (RelativeLayout) findViewById(R.id.axis_group_container);
        mwotGroupContainer = (RelativeLayout) findViewById(R.id.mwot_group_container);

        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        profileImage = (TouchHighlightImageButton) findViewById(R.id.image);
        mobileNumber = (TextView) findViewById(R.id.mobile_number);
        altContact = (TextView) findViewById(R.id.alt_contact_number);
        homePhone = (TextView) findViewById(R.id.home_phone_number);
        internalLandline = (TextView) findViewById(R.id.internal_landline_number);
        landline = (TextView) findViewById(R.id.landline_number);
        image = (ImageView) findViewById(R.id.image);
        postalAddress = (TextView) findViewById(R.id.postal_address);
        emailAddress = (TextView) findViewById(R.id.email_address);
        lineManager = (TextView) findViewById(R.id.line_manager);
        secretary = (TextView) findViewById(R.id.secretary);
        jobTitle = (TextView) findViewById(R.id.job_title);
        department = (TextView) findViewById(R.id.department);
        businessUnit = (TextView) findViewById(R.id.business_unit);
        division = (TextView) findViewById(R.id.division);
        userID = (TextView) findViewById(R.id.user_id);
        country = (TextView) findViewById(R.id.country);
        costCentre = (TextView) findViewById(R.id.cost_centre);
        website = (TextView) findViewById(R.id.website);
        addtnlLocation = (TextView) findViewById(R.id.additional_location);
        axisGroup = (TextView) findViewById(R.id.axis_group);
        mwotGroup = (TextView) findViewById(R.id.mwot_group);
    }

    private void setData(Colleague colleague, Bitmap image) {
        title.setText(colleague.getTitle());
        nameContainer.setVisibility(View.VISIBLE);
        name.setText(colleague.getName());

        if(image != null) {
            imageContainer.setVisibility(View.VISIBLE);
            profileImage.setImageBitmap(image);
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable d = new BitmapDrawable(getResources(), skeleton.getImageBitmap());

                    zoomImageFromThumb(profileImage, bitmap);
                }
            });
        }

        if(!colleague.getMobile().equals("")) {
            mobileContainer.setVisibility(View.VISIBLE);
            mobileNumber.setText(colleague.getMobile());
            Linkify.addLinks(mobileNumber  , Linkify.PHONE_NUMBERS);

            mobileNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        callDialer(mobileNumber.getText().toString());
                    } else {
                        startDialerActivity(mobileNumber.getText().toString());
                    }
                }
            });

        }
        if(!colleague.getAlternateContactNumber().equals("")) {
            alternateContactContainer.setVisibility(View.VISIBLE);
            altContact.setText(colleague.getAlternateContactNumber());
            Linkify.addLinks(altContact  , Linkify.PHONE_NUMBERS);

            altContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        callDialer(altContact.getText().toString());
                    } else {
                        startDialerActivity(altContact.getText().toString());
                    }
                }
            });
        }
        if(!colleague.getHomePhone().equals("")) {
            homePhoneContainer.setVisibility(View.VISIBLE);
            homePhone.setText(colleague.getHomePhone());
            Linkify.addLinks(homePhone  , Linkify.PHONE_NUMBERS);

            homePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        callDialer(homePhone.getText().toString());
                    } else {
                        startDialerActivity(homePhone.getText().toString());
                    }
                }
            });
        }
        if(!colleague.getInternalLandline().equals("")) {
            internalLandlineContainer.setVisibility(View.VISIBLE);
            internalLandline.setText(colleague.getInternalLandline());
            Linkify.addLinks(internalLandline  , Linkify.PHONE_NUMBERS);

            internalLandline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        callDialer(internalLandline.getText().toString());
                    } else {
                        startDialerActivity(internalLandline.getText().toString());
                    }
                }
            });
        }
        if(!colleague.getLandline().equals("")) {
            landlineContainer.setVisibility(View.VISIBLE);
            landline.setText(colleague.getLandline());
            Linkify.addLinks(landline  , Linkify.PHONE_NUMBERS);

            landline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        callDialer(landline.getText().toString());
                    } else {
                        startDialerActivity(landline.getText().toString());
                    }
                }
            });
        }
        if(!colleague.getPostalAddress().equals("")) {
            postalAddressContainer.setVisibility(View.VISIBLE);
            postalAddress.setText(colleague.getPostalAddress());
            postalAddress.setClickable(true);
            Linkify.addLinks(postalAddress, Linkify.ALL);
            postalAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMap();
                }
            });
        }
        if(!colleague.getEmailAddress().equals("")) {
            emailAddressContainer.setVisibility(View.VISIBLE);
            emailAddress.setText(colleague.getEmailAddress());
            Linkify.addLinks(emailAddress  , Linkify.EMAIL_ADDRESSES);
        }
        if(!colleague.getLineManager().equals("")) {
            lineManagerContainer.setVisibility(View.VISIBLE);
            lineManager.setText(colleague.getLineManager());
        }
        if(!colleague.getSecretary().equals("")) {
            secretaryContainer.setVisibility(View.VISIBLE);
            secretary.setText(colleague.getSecretary());
        }
        if(!colleague.getJobTitle().equals("")) {
            jobTitleContainer.setVisibility(View.VISIBLE);
            jobTitle.setText(colleague.getJobTitle());
        }
        if(!colleague.getDepartment().equals("")) {
            departmentContainer.setVisibility(View.VISIBLE);
            department.setText(colleague.getDepartment());
        }
        if(!colleague.getBusinessUnit().equals("")) {
            businessUnitContainer.setVisibility(View.VISIBLE);
            businessUnit.setText(colleague.getBusinessUnit());
        }
        if(!colleague.getDivision().equals("")) {
            divisionContainer.setVisibility(View.VISIBLE);
            division.setText(colleague.getDivision());
        }
        if(!colleague.getUserID().equals("")) {
            userIdContainer.setVisibility(View.VISIBLE);
            userID.setText(colleague.getUserID());
        }
        if(!colleague.getCountry().equals("")) {
            countryContainer.setVisibility(View.VISIBLE);
            country.setText(colleague.getCountry());
        }
        if(!colleague.getCostCentre().equals("")) {
            costCentreContainer.setVisibility(View.VISIBLE);
            costCentre.setText(colleague.getCostCentre());
        }
        if(!colleague.getWebsite().equals("")) {
            websiteContainer.setVisibility(View.VISIBLE);
            website.setText(colleague.getWebsite());
        }
        if(!colleague.getAddLocation().equals("")) {
            addtnlLocationContainer.setVisibility(View.VISIBLE);
            addtnlLocation.setText(colleague.getAddLocation());
        }
        if(!colleague.getAXISGroup().equals("")) {
            axisGroupContainer.setVisibility(View.VISIBLE);
            axisGroup.setText(colleague.getAXISGroup());
        }
        if(!colleague.getMWOTGroup().equals("")) {
            mwotGroupContainer.setVisibility(View.VISIBLE);
            mwotGroup.setText(colleague.getMWOTGroup());
        }
    }

    private void showMap() {
        String map = "http://maps.google.co.in/maps?q=" + postalAddress.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(i);
    }

    private void callDialer(String number) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            startDialerActivity(number);
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {

            Snackbar.make(mLayout, R.string.permission_call_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(ColleagueManagerDetailsActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    REQUEST_CALL);
                        }
                    })
                    .show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode == REQUEST_CALL) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.permision_available_call,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        //expandedImageView.setImageResource(R.drawable.close);
        //expandedImageView.setImageDrawable(imageResId);
        expandedImageView.setImageBitmap(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container1).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(200);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(200);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    private void startDialerActivity(String number) {
        String uri = "tel:" + number.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}

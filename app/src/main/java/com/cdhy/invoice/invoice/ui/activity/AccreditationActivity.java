package com.cdhy.invoice.invoice.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhy.invoice.invoice.CustomApplication;
import com.cdhy.invoice.invoice.R;
import com.cdhy.invoice.invoice.ui.MineUrl;
import com.cdhy.invoice.invoice.ui.util.DialogHb;
import com.cdhy.invoice.invoice.ui.util.MineUtil;
import com.cdhy.invoice.invoice.ui.util.UploadUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/5.
 */
public class AccreditationActivity extends Activity {
    Intent intent;
    String id;
    Button findcom, findsh;
    int type;
    TextView title;
    EditText kaihuhang, phone, name, yhzh, dz, shuihao;
    String skaihuhang, sPhone, sName, syhzh, sdz, sshuihao;
    ImageView gsyyzz, swdj, zzjg, szhy;
    RelativeLayout back, sanzengheyi_rl;
    Button sanzeng_btn, sanzengheyi_btn;
    private static String srcPath;
    LinearLayout pic;
    private String strImgPath = "";//照片保存路径
    private File imageFile = null;//照片文件
    Button bing;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            kaihuhang.setText(skaihuhang);
            phone.setText(sPhone);
            name.setText(sName);
            yhzh.setText(syhzh);
            dz.setText(sdz);
            shuihao.setText(sshuihao);
        }
    };
    Handler getHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            DialogHb.showdialog(AccreditationActivity.this, msg.obj.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_layout);
        init();
        getdata();
    }

    private void init() {
        intent = getIntent();
        id = intent.getStringExtra("id");
        bing = (Button) findViewById(R.id.bt_bing_current);
        findcom = (Button) findViewById(R.id.findcom_btn);
        findsh = (Button) findViewById(R.id.findsh_btn);
        kaihuhang = (EditText) findViewById(R.id.et_kaihuyinghang_current);
        phone = (EditText) findViewById(R.id.et_number_current);
        name = (EditText) findViewById(R.id.et_taitou_current);
        back = (RelativeLayout) findViewById(R.id.rl_back_current);
        shuihao = (EditText) findViewById(R.id.et_shuihao_current);
        yhzh = (EditText) findViewById(R.id.et_yhzh_current);
        dz = (EditText) findViewById(R.id.et_dizhi_current);
        title = (TextView) findViewById(R.id.current_title);
        gsyyzz = (ImageView) findViewById(R.id.current_frzz);
        swdj = (ImageView) findViewById(R.id.current_swdj);
        zzjg = (ImageView) findViewById(R.id.current_zzjg);
        pic = (LinearLayout) findViewById(R.id.current_line);
        szhy = (ImageView) findViewById(R.id.sanzhengheyi_img);
        sanzengheyi_rl = (RelativeLayout) findViewById(R.id.sanzhengheyi_rl);
        sanzeng_btn = (Button) findViewById(R.id.sanzehng);
        sanzengheyi_btn = (Button) findViewById(R.id.sanzengheyi);
        sanzengheyi_btn.setVisibility(View.VISIBLE);
        sanzeng_btn.setVisibility(View.VISIBLE);
        title.setText("企业认证");
        bing.setText("企业认证");
        findcom.setVisibility(View.GONE);
        findsh.setVisibility(View.GONE);
        listern();
    }

    private void getdata() {
        CustomApplication.index = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String resutl2 = MineUtil.postHttp(MineUrl.newHeads, getComById(id));
                rtnDatas(resutl2);
                Message message = new Message();
                handler.sendMessage(message);
            }
        }).start();
    }

    private void listern() {
        sanzengheyi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanzengheyi_rl.setVisibility(View.VISIBLE);
                sanzeng_btn.setVisibility(View.VISIBLE);
                pic.setVisibility(View.GONE);
                sanzengheyi_btn.setVisibility(View.GONE);
            }
        });
        sanzeng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanzengheyi_btn.setVisibility(View.VISIBLE);
                pic.setVisibility(View.VISIBLE);
                sanzengheyi_rl.setVisibility(View.GONE);
                sanzeng_btn.setVisibility(View.GONE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sshuihao = shuihao.getText().toString();
                sdz = dz.getText().toString();
                sPhone = phone.getText().toString();
                skaihuhang = kaihuhang.getText().toString();
                syhzh = yhzh.getText().toString();
                sName = name.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String s = MineUtil.postHttp(MineUrl.newHeads, addCompany(sshuihao, sdz, sPhone, skaihuhang, syhzh, sName));
                        Message message = new Message();
                        message.obj = rtnData(s);
                        getHandler.sendMessage(message);
                    }
                }).start();
            }
        });

        gsyyzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                openPictureSelectDialog();
            }
        });
        swdj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                openPictureSelectDialog();
            }
        });
        zzjg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 3;
                openPictureSelectDialog();
            }
        });
        szhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 4;
                openPictureSelectDialog();
            }
        });
    }

    public String getComById(String custinfo) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "getCardById");
            JSONObject js = new JSONObject();
            js.put("id", custinfo);
            js.put("type", "Android");
            js.put("userid", CustomApplication.userId);
            js.put("athID", CustomApplication.athID);
            js.put("client", CustomApplication.DEVICE_ID);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("phone", url);
        return url;
    }

    private String rtnDatas(String resutl) {
        String rtndata = null;
        if (resutl != null) {
            try {
                JSONObject jsonObject = new JSONObject(resutl);
                rtndata = jsonObject.getString("msg");
                if (jsonObject.getString("resultType").equals("SUCCESS")) {
                    JSONObject array = new JSONObject(jsonObject.getString("data"));
                    skaihuhang = array.optString("KHH");
                    sPhone = array.optString("PHONE");
                    sshuihao = array.optString("NSRSBH");
                    sName = array.optString("NAME");
                    sdz = array.optString("ADDRESS");
                    syhzh = array.optString("YHZH");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rtndata;
    }

    public String addCompany(String nsrsbh, String address, String phone, String khh, String yhzh, String name) {
        String url = null;
        JSONObject json = new JSONObject();
        try {
            json.put("invoke", "addCompany");
            JSONObject js = new JSONObject();
            js.put("userid", CustomApplication.userId);
            js.put("athID", CustomApplication.athID);
            js.put("type", "Android");
            js.put("nsrsbh", nsrsbh);
            js.put("id", id);
            js.put("address", address);
            js.put("phone", phone);
            js.put("khh", khh);
            js.put("yhzh", yhzh);
            js.put("name", name);
            json.put("p", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = json.toString();
        Log.e("phone", url);
        return url;
    }


    private void openPictureSelectDialog() {
        Context dialogContext = new ContextThemeWrapper(
                AccreditationActivity.this, android.R.style.Theme_Light);
        String[] choiceItems = new String[2];
        choiceItems[0] = "相机拍照";
        choiceItems[1] = "相册选择";
        ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
                android.R.layout.simple_list_item_1, choiceItems);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogContext);
        builder.setTitle("上传图片");
        builder.setSingleChoiceItems(adapter, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent getPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                strImgPath = Environment.getExternalStorageDirectory()
                                        .toString() + "/Xiong_PIC/";
                                String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
                                        .format(new Date()) + ".jpg";// 照片以格式化日期方式命名
                                File out = new File(strImgPath);
                                if (!out.exists()) {
                                    out.mkdirs();
                                }
                                out = new File(strImgPath, fileName);
                                strImgPath = strImgPath + fileName;// 该照片的绝对路径
                                Uri uri = Uri.fromFile(out);
                                getPhoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);//根据uri保存照片
                                getPhoto.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//保存照片的质量
                                startActivityForResult(getPhoto, 1);//启动相机拍照
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 2);
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    imageFile = new File(strImgPath);
                    int scale = 2;
                    scale = getZoomScale(imageFile);//得到缩放倍数
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = scale;
                    srcPath = strImgPath;
                    final File file = new File(srcPath);
                    if (type == 1) {
                        gsyyzz.setImageBitmap(BitmapFactory.decodeFile(strImgPath, options));//按指定options显示图片防止OOM
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String s = UploadUtil.uploadFile(
                                        file,
                                        MineUrl.uploda
                                                + "?" + "athID="
                                                + CustomApplication.athID
                                                + "&type=Android&nsrsbh="
                                                + shuihao.getText().toString() + "&zjlx=gsyyzz");
                                String data = rtnData(s);
                                if (!data.equals("成功")) {
                                    Message message = new Message();
                                    message.obj = data;
                                    getHandler.sendMessage(message);
                                }
                            }
                        }).start();
                    } else if (type == 2) {
                        swdj.setImageBitmap(BitmapFactory.decodeFile(strImgPath, options));//按指定options显示图片防止OOM
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String s = UploadUtil.uploadFile(file, MineUrl.uploda + "?" + "athID=" +
                                        CustomApplication.athID + "&type=Android&nsrsbh=" + shuihao.getText().toString()
                                        + "&zjlx=swdjz");
                                String data = rtnData(s);
                                if (!data.equals("成功")) {
                                    Message message = new Message();
                                    message.obj = data;
                                    getHandler.sendMessage(message);
                                }
                            }
                        }).start();
                    } else if (type == 3) {
                        zzjg.setImageBitmap(BitmapFactory.decodeFile(strImgPath, options));//按指定options显示图片防止OOM
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String s = UploadUtil.uploadFile(file, MineUrl.uploda + "?" + "athID="
                                        + CustomApplication.athID + "&type=Android&nsrsbh=" + shuihao.getText().toString() + "&zjlx=zzjgdmz");
                                String data = rtnData(s);
                                if (!data.equals("成功")) {
                                    Message message = new Message();
                                    message.obj = data;
                                    getHandler.sendMessage(message);
                                }
                            }
                        }).start();
                    } else if (type == 4) {
                        szhy.setImageBitmap(BitmapFactory.decodeFile(strImgPath, options));//按指定options显示图片防止OOM
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String s = UploadUtil.uploadFile(file, MineUrl.uploda + "?" + "athID="
                                        + CustomApplication.athID + "&type=Android&nsrsbh=" + shuihao.getText().toString()
                                        + "&zjlx=szhy");
                                String data = rtnData(s);
                                if (!data.equals("成功")) {
                                    Message message = new Message();
                                    message.obj = data;
                                    getHandler.sendMessage(message);
                                }
                            }
                        }).start();
                    }
                    break;
                case 2:
                    Bitmap bm2 = null;
                    ContentResolver resolver = getContentResolver();
                    try {
                        final Uri originalUri = data.getData();        //获得图片的uri

                        bm2 = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片

                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        srcPath = cursor.getString(column_index);
                        final File files = new File(srcPath);
                        imageFile = new File(srcPath);
                        int scale2 = 2;
                        scale = getZoomScale(files);//得到缩放倍数
                        BitmapFactory.Options options2 = new BitmapFactory.Options();
                        options2.inSampleSize = scale2;
                        final File file2 = new File(srcPath);

                        if (type == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String s = UploadUtil.uploadFile(files, MineUrl.uploda + "?" + "athID="
                                            + CustomApplication.athID + "&type=Android&nsrsbh="
                                            + shuihao.getText().toString() + "&zjlx=gsyyzz");
                                    String data = rtnData(s);
                                    if ("" != data || null != data) {
                                        if (!data.equals("成功")) {
                                            Message message = new Message();
                                            message.obj = data;
                                            getHandler.sendMessage(message);
                                        }
                                    }
                                }
                            }).start();
                            gsyyzz.setImageBitmap(BitmapFactory.decodeFile(srcPath, options2));//按指定options显示图片防止OOM
                        } else if (type == 2) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String s = UploadUtil.uploadFile(files, MineUrl.uploda + "?" + "athID="
                                            + CustomApplication.athID + "&type=Android&nsrsbh="
                                            + shuihao.getText().toString() + "&zjlx=swdjz");
                                    String data = rtnData(s);
                                    if ("" != data || null != data) {
                                        if (!data.equals("成功")) {
                                            Message message = new Message();
                                            message.obj = data;
                                            getHandler.sendMessage(message);
                                        }
                                    }
                                }
                            }).start();
                            swdj.setImageBitmap(BitmapFactory.decodeFile(srcPath, options2));//按指定options显示图片防止OOM
                        } else if (type == 3) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String s = UploadUtil.uploadFile(files, MineUrl.uploda + "?" + "athID="
                                            + CustomApplication.athID + "&type=Android&nsrsbh="
                                            + shuihao.getText().toString() + "&zjlx=zzjgdmz");
                                    String data = rtnData(s);
                                    if (!data.equals("成功")) {
                                        Message message = new Message();
                                        message.obj = data;
                                        getHandler.sendMessage(message);
                                    }
                                }
                            }).start();
                            zzjg.setImageBitmap(BitmapFactory.decodeFile(srcPath, options2));//按指定options显示图片防止OOM
                        } else if (type == 4) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String s = UploadUtil.uploadFile(files, MineUrl.uploda + "?" + "athID="
                                            + CustomApplication.athID + "&type=Android&nsrsbh="
                                            + shuihao.getText().toString() + "&zjlx=szhy");
                                    String data = rtnData(s);
                                    if (!data.equals("成功")) {
                                        Message message = new Message();
                                        message.obj = data;
                                        getHandler.sendMessage(message);
                                    }
                                }
                            }).start();
                            szhy.setImageBitmap(BitmapFactory.decodeFile(srcPath, options2));//按指定options显示图片防止OOM
                        }
                    } catch (IOException e) {
                        Log.e("this", e.toString());
                    }
                    break;
            }
            ;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int getZoomScale(File imageFile) {
        int scale = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(strImgPath, options);
        while (options.outWidth / scale >= 320
                || options.outHeight / scale >= 480) {
            scale *= 2;
        }
        return scale;
    }

    private String rtnData(String resutl) {
        String rtndata = null;
        if (resutl != null) {
            try {
                JSONObject jsonObject = new JSONObject(resutl);
                rtndata = jsonObject.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rtndata;
    }


}

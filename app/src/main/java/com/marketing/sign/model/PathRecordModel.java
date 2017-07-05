package com.marketing.sign.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.marketing.sign.db.annotation.Column;
import com.marketing.sign.db.annotation.Id;
import com.marketing.sign.db.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于记录一条轨迹，包括起点、终点、轨迹中间点、距离、耗时、平均速度、时间
 *
 * @author shixq
 */
@Table(name = PathRecordModel.TABLE_NAME)
public class PathRecordModel implements Parcelable {
    public static final String ID = "_id";
    public static final String TABLE_NAME = "path";
    public static final String START_POINT = "stratpoint";
    public static final String END_POINT = "endpoint";
    public static final String PATH_LINE = "pathline";
    public static final String DISTANCE = "distance";
    public static final String DURATION = "duration";
    public static final String AVERAGE_SPEED = "averagespeed";
    public static final String DATE = "date";
    private AMapLocation mStartPoint;
    private AMapLocation mEndPoint;
    private List<AMapLocation> mPathLinePoints = new ArrayList<AMapLocation>();
    @Id
    @Column(name = ID)
    protected int id;
    @Column(name = PathRecordModel.START_POINT)
    private String mStartPointTxt;
    @Column(name = PathRecordModel.END_POINT)
    private String mEndPointTxt;
    @Column(name = PathRecordModel.DISTANCE)
    private String mDistance;
    @Column(name = PathRecordModel.PATH_LINE)
    private String mPathLine;
    @Column(name = PathRecordModel.DURATION)
    private String mDuration;
    @Column(name = PathRecordModel.AVERAGE_SPEED)
    private String mAveragespeed;
    @Column(name = PathRecordModel.DATE)
    private String mDate;

    public PathRecordModel() {

    }

    protected PathRecordModel(Parcel in) {
        mStartPoint = in.readParcelable(AMapLocation.class.getClassLoader());
        mEndPoint = in.readParcelable(AMapLocation.class.getClassLoader());
        mPathLinePoints = in.createTypedArrayList(AMapLocation.CREATOR);
        id = in.readInt();
        mStartPointTxt = in.readString();
        mEndPointTxt = in.readString();
        mDistance = in.readString();
        mPathLine = in.readString();
        mDuration = in.readString();
        mAveragespeed = in.readString();
        mDate = in.readString();
    }

    public static final Creator<PathRecordModel> CREATOR = new Creator<PathRecordModel>() {
        @Override
        public PathRecordModel createFromParcel(Parcel in) {
            return new PathRecordModel(in);
        }

        @Override
        public PathRecordModel[] newArray(int size) {
            return new PathRecordModel[size];
        }
    };

    public AMapLocation getStartpoint() {
        return mStartPoint;
    }

    public void setStartpoint(AMapLocation startpoint) {
        this.mStartPoint = startpoint;
    }

    public AMapLocation getEndpoint() {
        return mEndPoint;
    }

    public void setEndpoint(AMapLocation endpoint) {
        this.mEndPoint = endpoint;
    }

    public List<AMapLocation> getPathline() {
        return mPathLinePoints;
    }

    public void setPathline(List<AMapLocation> pathline) {
        this.mPathLinePoints = pathline;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        this.mDistance = distance;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }

    public String getAveragespeed() {
        return mAveragespeed;
    }

    public void setAveragespeed(String averagespeed) {
        this.mAveragespeed = averagespeed;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public void addpoint(AMapLocation point) {
        mPathLinePoints.add(point);
    }

    public String getPathLineString(List<AMapLocation> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer pathline = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            AMapLocation location = list.get(i);
            String locString = amapLocationToString(location);
            pathline.append(locString).append(";");
        }
        String pathLineString = pathline.toString();
        pathLineString = pathLineString.substring(0,
                pathLineString.length() - 1);
        return pathLineString;
    }

    public String amapLocationToString(AMapLocation location) {
        StringBuffer locString = new StringBuffer();
        locString.append(location.getLatitude()).append(",");
        locString.append(location.getLongitude()).append(",");
        locString.append(location.getProvider()).append(",");
        locString.append(location.getTime()).append(",");
        locString.append(location.getSpeed()).append(",");
        locString.append(location.getBearing());
        return locString.toString();
    }


    @Override
    public String toString() {
        StringBuilder record = new StringBuilder();
        record.append("recordSize:" + getPathline().size() + ", ");
        record.append("distance:" + getDistance() + "m, ");
        record.append("duration:" + getDuration() + "s");
        return record.toString();
    }

    public float getDistance(List<AMapLocation> list) {
        float distance = 0;
        if (list == null || list.size() == 0) {
            return distance;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            AMapLocation firstpoint = list.get(i);
            AMapLocation secondpoint = list.get(i + 1);
            LatLng firstLatLng = new LatLng(firstpoint.getLatitude(),
                    firstpoint.getLongitude());
            LatLng secondLatLng = new LatLng(secondpoint.getLatitude(),
                    secondpoint.getLongitude());
            double betweenDis = AMapUtils.calculateLineDistance(firstLatLng,
                    secondLatLng);
            distance = (float) (distance + betweenDis);
        }
        return distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mStartPoint, flags);
        dest.writeParcelable(mEndPoint, flags);
        dest.writeTypedList(mPathLinePoints);
        dest.writeInt(id);
        dest.writeString(mStartPointTxt);
        dest.writeString(mEndPointTxt);
        dest.writeString(mDistance);
        dest.writeString(mPathLine);
        dest.writeString(mDuration);
        dest.writeString(mAveragespeed);
        dest.writeString(mDate);
    }
}

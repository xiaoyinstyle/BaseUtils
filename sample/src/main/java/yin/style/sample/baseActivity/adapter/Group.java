package yin.style.sample.baseActivity.adapter;

import java.util.List;

public class Group {
    //分组名称
    private String mGroupName;
    //分组项目
    private List<GroupItem> mGroupItems;

    public Group(String GroupName, List<GroupItem> GroupItems) {
        this.mGroupName = GroupName;
        this.mGroupItems = GroupItems;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public List<GroupItem> getGroupItems() {
        return mGroupItems;
    }

    public void setGroupItems(List<GroupItem> mItems) {
        this.mGroupItems = mItems;
    }

    public static class GroupItem {

        private String Title;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public GroupItem() {
        }

        public GroupItem(String mTitle) {
            this.Title = mTitle;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

    }

}

package usermanagement;

public class BookList {

    private String titlename;
    private String authorname;
    long id_user;

    public BookList( String authorname, String titlename,long id_user) {
        this.titlename = titlename;
        this.authorname = authorname;
        this.id_user=id_user;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }


    public BookList() {
    }

    @Override
    public String toString() {
        return "BookList{" +
                "title='" + titlename + '\'' +
                ", author='" + authorname + '\'' +
                ", id_user=" + id_user +
                '}';
    }
}

package yams.hight_scores.pojos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SuppressWarnings({"java:S116", "java:S117", "java:S2143"})
public class Score implements Serializable{
    // conserve le SUID historique (champs _id/_name/_score/_date) pour rester compatible avec les .dat déjà sur le disque des joueurs
    private static final long serialVersionUID = 2642149569287590548L;

    private static final String CHAMP_SCORE = "score";
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // déclare les anciens ET les nouveaux noms de champs pour pouvoir lire les deux formats via readObject ci-dessous
    // le champ "date"/"_date" reste de type Date : c'est le type réellement présent dans les .dat déjà sur le disque
    @SuppressWarnings("java:S1068")
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("id", int.class),
        new ObjectStreamField("name", String.class),
        new ObjectStreamField(CHAMP_SCORE, int.class),
        new ObjectStreamField("date", Date.class),
        new ObjectStreamField("_id", int.class),
        new ObjectStreamField("_name", String.class),
        new ObjectStreamField("_score", int.class),
        new ObjectStreamField("_date", Date.class),
    };

    private int id;
    private String name;
    private int points;
    private LocalDate date;

    public Score(String name, int score) {
        this.name = name;
        this.points = score;
        this.date = LocalDate.now(ZoneId.systemDefault());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return points;
    }

    public void setScore(int score) {
        this.points = score;
    }

    public String getDate(){
        if(date == null){
            return "";
        }
        return date.format(FORMAT_DATE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        ObjectOutputStream.PutField fields = oos.putFields();
        fields.put("id", id);
        fields.put("name", name);
        fields.put(CHAMP_SCORE, points);
        fields.put("date", toDate(date));
        oos.writeFields();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = ois.readFields();
        this.id = fields.defaulted("id") ? fields.get("_id", 0) : fields.get("id", 0);
        this.name = (String) (fields.defaulted("name") ? fields.get("_name", (Object) null) : fields.get("name", (Object) null));
        this.points = fields.defaulted(CHAMP_SCORE) ? fields.get("_score", 0) : fields.get(CHAMP_SCORE, 0);
        Date dateBrute = (Date) (fields.defaulted("date") ? fields.get("_date", (Object) null) : fields.get("date", (Object) null));
        this.date = toLocalDate(dateBrute);
    }

    private static Date toDate(LocalDate localDate){
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDate toLocalDate(Date date){
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

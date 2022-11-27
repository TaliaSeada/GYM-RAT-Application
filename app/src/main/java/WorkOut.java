//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.LinkedList;
//
//public class WorkOut {
//    private LinkedList<exercise> exercises;
//    private DatabaseReference mDatabase;
//
//    public WorkOut(){
//        this.exercises = new LinkedList<exercise>();
//        this.mDatabase = FirebaseDatabase.getInstance().getReference();
//    }
//
//    public LinkedList getExe(){
//        return this.exercises;
//    }
//
//    public void addExe(String name, int sets, int rep, double weight) {
//        exercise new_ex = new exercise(name, sets, rep, weight);
//        exercises.add(new_ex);
//        this.mDatabase.child(name).child(exercises.size() + "").setValue(new_ex);
//    }
//
//    public void removeExe(exercise name){
//        exercises.remove(name);
//    }
//
//    public void updateExeSets(exercise name, int sets){
//        int i = exercises.indexOf(name);
//        exercises.get(i).sets = sets;
//    }
//
//    public void updateExeRep(exercise name, int rep){
//        int i = exercises.indexOf(name);
//        exercises.get(i).repetition = rep;
//    }
//
//    public void updateExeWeight(exercise name, int weight){
//        int i = exercises.indexOf(name);
//        exercises.get(i).weight_kg = weight;
//    }
//
//    public void clearExe(){
//        exercises.clear();
//    }
//
//    private static class exercise{
//        String name;
//        int sets;
//        int repetition;
//        double weight_kg;
//
//        public exercise(String name, int sets, int rep, double weight_kg) {
//            this.name = name;
//            this.sets = sets;
//            this.repetition = rep;
//            this.weight_kg = weight_kg;
//        }
//    }
//}
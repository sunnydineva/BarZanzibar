package models;

public enum UserType {
        WAITRESS("W-Сервитьор",1),
        MANAGER("M-Мениджър", 2);

        public final int no;
        public final String label;

        UserType(String label, int no){
                this.label = label;
                this.no = no;
        }
}

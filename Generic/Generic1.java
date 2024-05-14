public class Generic1 <T> {
        private T data;
        
        public Generic1(T data) {
            this.data = data;
        }
        
        public T getData() {
            return data;
        }
        
        public void displayData(){
        System.out.println("Tipe data : "+data.getClass().getName());
    }
        
    }

class Ciao{
	public static void main (String[] args) {
		System.out.println("ciao");
		System.out.println(7+90);
		int x = Integer.parseInt(args [0]);
        int y = Integer.parseInt(args [1]);
        System.out.println(x+y);
		double z = Double.parseDouble(args [2]);
        double v = Double.parseDouble(args [3]);
		System.out.println(z/v);
		System.out.println("resto " + (z%v));
	}
}
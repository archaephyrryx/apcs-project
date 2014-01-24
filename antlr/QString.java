class QString {
    public static String unQuote(String s) {
	s = s.replaceAll("\\\\(.)", "\\1");
	s = s.replaceAll("^\\\"", "");
	s = s.replaceAll("\\\"$", "");
	return s;
    }
}

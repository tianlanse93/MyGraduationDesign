package com.scut.zl;

import abner.Trainer;

public class Trainner {
	public static void train(String trainTextPath,String modelTextPath){
		Trainer t = new Trainer();
		t.train(trainTextPath, modelTextPath);
	}
}

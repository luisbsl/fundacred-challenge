package br.com.fundacred.challenge.util;

public final class EnviromentHelper {

	private EnviromentHelper() {
	}

	public enum Key {
		FUNDACRED_CHALLENGE_API_SECRET {
			@Override
			public String getVal() {
				return System.getenv(this.toString());
			}
		},
		FUNDACRED_CHALLENGE_JWT_ID {
			@Override
			public String getVal() {
				return System.getenv(this.toString());
			}
		},
		FUNDACRED_CHALLENGE_API_NAME {
			@Override
			public String getVal() {
				return System.getenv(this.toString());
			}
		};

		public abstract String getVal();
	}

}

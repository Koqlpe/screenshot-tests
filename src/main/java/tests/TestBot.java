package tests;

public class TestBot {
    private static final String defaultBotUsername = "technopol33";
    private static final String defaultBotPassword = "technopolisPassword";
    private static final long defaultId = 587302494564L;

    private String botUsername;
    private String botPassword;
    private long id;

    private TestBot() {
        botUsername = defaultBotUsername;
        botPassword = defaultBotPassword;
        id = defaultId;
    }

    public String getBotUsername() {
        return botUsername;
    }
    public String getBotPassword() {
        return botPassword;
    }
    public long getId() {
        return id;
    }

    public static Builder newBuilder() {
        return new TestBot().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setPassword(String password) {
            TestBot.this.botPassword = password;
            return this;
        }

        public Builder setUsername(String username) {
            TestBot.this.botUsername = username;
            return this;
        }

        public Builder setId(long id) {
            TestBot.this.id = id;
            return this;
        }

        public TestBot build() {
            return TestBot.this;
        }

        public TestBot buildDefault() {
            return setUsername(defaultBotUsername)
                    .setPassword(defaultBotPassword)
                    .setId(defaultId)
                    .build();
        }
    }
}

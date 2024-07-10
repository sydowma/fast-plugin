package com.github.sydowma.fast.development.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author plexpt
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**
     * 目前支持三种角色参考官网，进行情景输入：https://platform.openai.com/docs/guides/chat/introduction
     */
    private String role;
    private String content;
    private String name;


    @JsonProperty("tool_calls")
    private List<ToolCallResult> toolCalls;

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static Message of(String content) {

        return new Message(Role.USER.getValue(), content);
    }

    public static Message ofSystem(String content) {

        return new Message(Role.SYSTEM.getValue(), content);
    }

    public static Message ofAssistant(String content) {

        return new Message(Role.ASSISTANT.getValue(), content);
    }

    public static Message ofFunction(String function) {

        return new Message(Role.FUNCTION.getValue(), function);
    }

    public enum Role {

        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant"),

        FUNCTION("function"),
        ;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private String value;
    }

    public Message() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToolCallResult> getToolCalls() {
        return toolCalls;
    }

    public void setToolCalls(List<ToolCallResult> toolCalls) {
        this.toolCalls = toolCalls;
    }
}

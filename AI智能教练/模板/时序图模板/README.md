@startuml
title 课程发布及AI陪练生成流程时序图


skinparam sequence {
ParticipantPadding 100
BoxPadding 30
ArrowColor #555555
}

actor 用户

box "调度质检系统"
participant "课程管理服务" as CMS
database "课程数据库" as DB
end box

participant "AI陪练系统" as AI


== 发布课程 ==


用户 -> CMS : 点击发布课程


CMS -> DB : 查询课程配置\n是否开启陪练

DB --> CMS : 返回配置结果



alt 未开启陪练

    CMS -> DB : 更新课程状态\n已发布

    DB --> CMS : 更新成功


else 开启陪练


    CMS -> DB : 更新课程状态\n发布中


    CMS -> AI : 发送课程信息\n生成AI陪练问答


    AI --> CMS : 返回任务ID\n接收成功



    note right of AI
    异步生成：
    1.课程解析
    2.知识抽取
    3.生成陪练问答
    end note



    alt AI生成成功

        AI -> CMS : 调用接口通知\n生成成功

        CMS -> DB : 更新课程状态\n已发布


    else AI生成失败


        AI -> CMS : 调用接口通知\n生成失败+原因


        CMS -> DB : 更新课程状态\n发布失败


    end

end



== 发布失败处理 ==


用户 -> CMS : 查看发布失败课程


alt 编辑重新发布

    用户 -> CMS : 编辑课程

    CMS -> DB : 保存课程信息


    用户 -> CMS : 重新发布

    CMS -> DB : 更新状态\n发布中


    CMS -> AI : 重新生成陪练


else 删除课程

    用户 -> CMS : 删除课程

    CMS -> DB : 更新状态\n已删除


end


@enduml
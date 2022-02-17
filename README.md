<div align="center">
<h1>Car Fixers</h1>
    <img alt="img" src="https://i.imgur.com/hJgJIu8.png" />
</div>

A framework-less Java web app that allows an auto-repair company to manage and track tasks. **The frontend design/CSS in this repo isn't mine.**

- Users with different roles have access to different functions.
- Tasks have a comments section.

## Content
- [The Company's Structure](#the-companys-structure)
- [Task Status](#task-status)
- [Typical Workflow](#typical-workflow)

## The Company's Structure
The company has a **CEO** and four **departments**, each managed by a **department head**:
 - Repair
 - Quality Assurance
 - Accounting
 - Customer Support
 
Each **department** has a number of **groups**. Each **group** has multiple **group members** and is managed by a **group head**.

Thus, a user can have one of the following roles:
 
 - CEO
    - Registers new employees
    - Has access to general admin tasks
 - Head of department 
    - Registers new tasks
    - Assigns tasks to groups
 - Head of group
    - Assigns tasks to group members
 - Group member 
    - Works on tasks assigned to him
    
## Task Status    

- Available: a new task that has no one working on it yet.
- Assigned: the task has a **group member** working on it.
- Complete: the assigned **group member** has completed the task.
- Confirmed_Complete: the task is approved to be complete by a **group head**.
- Has_Issues: a **group member** is facing issues with the task and can't complete it.
    
## Typical Workflow

1) The **head of department** creates a new task and assigns it to some **group**. During this phase, the task has the status of **available** with no assignee yet.

<div align="center">
    <img alt="img" src="https://i.imgur.com/4gqXJX4.png" />
    <br />
    <img alt="img" src="https://i.imgur.com/wYRKY2n.png" />
</div>

&nbsp;

2) The **head of the group** assigns the task to a **group member**, causing the task's status to change to **assigned**.  

<div align="center">
    <img alt="img" src="https://i.imgur.com/M9whjQr.png" />
    <br />
    <img alt="img" src="https://i.imgur.com/BvxkW8z.png" />
</div>

&nbsp;

3) The **group member** responsible for a task can either mark it as **complete** after finishing it, or as **has_issues** if he faces issues that prevent him from completing it.

<div align="center">
    <img alt="img" src="https://i.imgur.com/wwHu6f8.png" />
</div>

&nbsp;

4) The **group head** can then review **complete** tasks and, if he approves of them, mark them as **confirmed_complete**.

<div align="center">
    <img alt="img" src="https://i.imgur.com/yjJ2Miy.png" />
</div>
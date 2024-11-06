User Story: User with admin role can modify user list.
1. Admin can add user
   a. Display button "Add User" only if current user has admin role.
   b. On button click, display form.
   c. On form submit, redirect to user list page.
2. Admin can view all users
   a. Display button "User List" only if User has admin role.
3. Admin can edit user details
   a. Display "edit" button next to user.
   b. Open form on button click.
   c. On form submit, redirect back to user list page.

User Story: User with manager role can modify projects managed by them.
1. Manager can add Project
   a. Display "Add Project" button only if user has admin/manager role.
   b. On button click, open form.
   c. On form submission, user is redirected to the project's page.
   d. All developers assigned to the project are sent notifications.
2. Manager can edit project
   a. Display "edit" button in project page only if user is manager.
   b. Display edit form on button click.
   c. Redirect to project page on form submission.
3. Manager can delete project
   a. Display "delete" button next to project in project list.
   b. On button click, delete project and redirect back to project list.
4. Manager can remove users assigned to project.

User Story: User with manager role and developers with edit access can add/remove/edit tickets.
1. User can add ticket.
2. User can update ticket.
3. User can delete ticket.
   
User Story: User can see an overview of their work in the dashboard.
1. Display graphs showing work done, and work left.
   a. Tickets assigned vs completed.
   b. Tickets completed by priority.
2. Get Projects where user is either manager or developer.
   a. Load Projects on Dashboard component render.
3. Get Tickets where user is assigner or assignee.
   a. sort the list by last updated/created.
4. See pending tasks and updates.
   a. Get List of tasks (tickets)
   b. Can switch tabs between all tickets, high priority tickets, and latest updated.

User Story: Developer should be able to comment on tickets assigned to them.

User Story: Assignee can send review request to Assigner after ticket completion with link to Github pull request.
1. Add Button next to assigner to "Ask Review".
2. Open Dialog Box to paste in Github Link.
3. Send notification to assigner and add to assigner pending tasks.



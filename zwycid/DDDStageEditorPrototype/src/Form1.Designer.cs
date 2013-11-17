namespace DDDMapEditorPrototype
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator = new System.Windows.Forms.ToolStripSeparator();
            this.saveToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.editToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.undoToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.redoToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator3 = new System.Windows.Forms.ToolStripSeparator();
            this.cutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.copyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.pasteToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripSeparator4 = new System.Windows.Forms.ToolStripSeparator();
            this.selectAllToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.optionsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.helpToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripTextBox();
            this.toolPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.selectorRadio = new System.Windows.Forms.RadioButton();
            this.label1 = new System.Windows.Forms.Label();
            this.startRadio = new System.Windows.Forms.RadioButton();
            this.goalRadio = new System.Windows.Forms.RadioButton();
            this.label2 = new System.Windows.Forms.Label();
            this.obstacleRadio = new System.Windows.Forms.RadioButton();
            this.obstacleInvisibleRadio = new System.Windows.Forms.RadioButton();
            this.label3 = new System.Windows.Forms.Label();
            this.attractorRadio = new System.Windows.Forms.RadioButton();
            this.tracerRadio = new System.Windows.Forms.RadioButton();
            this.viewPanel = new System.Windows.Forms.Panel();
            this.stageView = new DDDMapEditorPrototype.BufferedPanel();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.coordLabel = new System.Windows.Forms.ToolStripStatusLabel();
            this.menuStrip1.SuspendLayout();
            this.toolPanel.SuspendLayout();
            this.viewPanel.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.editToolStripMenuItem,
            this.toolsToolStripMenuItem,
            this.helpToolStripMenuItem,
            this.toolStripMenuItem1});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(735, 27);
            this.menuStrip1.TabIndex = 2;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newToolStripMenuItem,
            this.openToolStripMenuItem,
            this.toolStripSeparator,
            this.saveToolStripMenuItem,
            this.saveAsToolStripMenuItem,
            this.toolStripSeparator1,
            this.exitToolStripMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 23);
            this.fileToolStripMenuItem.Text = "&File";
            // 
            // newToolStripMenuItem
            // 
            this.newToolStripMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("newToolStripMenuItem.Image")));
            this.newToolStripMenuItem.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.newToolStripMenuItem.Name = "newToolStripMenuItem";
            this.newToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.N)));
            this.newToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.newToolStripMenuItem.Text = "&New";
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("openToolStripMenuItem.Image")));
            this.openToolStripMenuItem.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.O)));
            this.openToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.openToolStripMenuItem.Text = "&Open";
            // 
            // toolStripSeparator
            // 
            this.toolStripSeparator.Name = "toolStripSeparator";
            this.toolStripSeparator.Size = new System.Drawing.Size(143, 6);
            // 
            // saveToolStripMenuItem
            // 
            this.saveToolStripMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("saveToolStripMenuItem.Image")));
            this.saveToolStripMenuItem.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.saveToolStripMenuItem.Name = "saveToolStripMenuItem";
            this.saveToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.S)));
            this.saveToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.saveToolStripMenuItem.Text = "&Save";
            // 
            // saveAsToolStripMenuItem
            // 
            this.saveAsToolStripMenuItem.Name = "saveAsToolStripMenuItem";
            this.saveAsToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.saveAsToolStripMenuItem.Text = "Save &As";
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(143, 6);
            // 
            // exitToolStripMenuItem
            // 
            this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
            this.exitToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.exitToolStripMenuItem.Text = "E&xit";
            // 
            // editToolStripMenuItem
            // 
            this.editToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.undoToolStripMenuItem,
            this.redoToolStripMenuItem,
            this.toolStripSeparator3,
            this.cutToolStripMenuItem,
            this.copyToolStripMenuItem,
            this.pasteToolStripMenuItem,
            this.toolStripSeparator4,
            this.selectAllToolStripMenuItem});
            this.editToolStripMenuItem.Name = "editToolStripMenuItem";
            this.editToolStripMenuItem.Size = new System.Drawing.Size(39, 23);
            this.editToolStripMenuItem.Text = "&Edit";
            // 
            // undoToolStripMenuItem
            // 
            this.undoToolStripMenuItem.Name = "undoToolStripMenuItem";
            this.undoToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Z)));
            this.undoToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
            this.undoToolStripMenuItem.Text = "&Undo";
            // 
            // redoToolStripMenuItem
            // 
            this.redoToolStripMenuItem.Name = "redoToolStripMenuItem";
            this.redoToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Y)));
            this.redoToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
            this.redoToolStripMenuItem.Text = "&Redo";
            // 
            // toolStripSeparator3
            // 
            this.toolStripSeparator3.Name = "toolStripSeparator3";
            this.toolStripSeparator3.Size = new System.Drawing.Size(141, 6);
            // 
            // cutToolStripMenuItem
            // 
            this.cutToolStripMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("cutToolStripMenuItem.Image")));
            this.cutToolStripMenuItem.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.cutToolStripMenuItem.Name = "cutToolStripMenuItem";
            this.cutToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.X)));
            this.cutToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
            this.cutToolStripMenuItem.Text = "Cu&t";
            // 
            // copyToolStripMenuItem
            // 
            this.copyToolStripMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("copyToolStripMenuItem.Image")));
            this.copyToolStripMenuItem.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.copyToolStripMenuItem.Name = "copyToolStripMenuItem";
            this.copyToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.C)));
            this.copyToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
            this.copyToolStripMenuItem.Text = "&Copy";
            // 
            // pasteToolStripMenuItem
            // 
            this.pasteToolStripMenuItem.Image = ((System.Drawing.Image)(resources.GetObject("pasteToolStripMenuItem.Image")));
            this.pasteToolStripMenuItem.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.pasteToolStripMenuItem.Name = "pasteToolStripMenuItem";
            this.pasteToolStripMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.V)));
            this.pasteToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
            this.pasteToolStripMenuItem.Text = "&Paste";
            // 
            // toolStripSeparator4
            // 
            this.toolStripSeparator4.Name = "toolStripSeparator4";
            this.toolStripSeparator4.Size = new System.Drawing.Size(141, 6);
            // 
            // selectAllToolStripMenuItem
            // 
            this.selectAllToolStripMenuItem.Name = "selectAllToolStripMenuItem";
            this.selectAllToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
            this.selectAllToolStripMenuItem.Text = "Select &All";
            // 
            // toolsToolStripMenuItem
            // 
            this.toolsToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.optionsToolStripMenuItem});
            this.toolsToolStripMenuItem.Name = "toolsToolStripMenuItem";
            this.toolsToolStripMenuItem.Size = new System.Drawing.Size(47, 23);
            this.toolsToolStripMenuItem.Text = "&Tools";
            // 
            // optionsToolStripMenuItem
            // 
            this.optionsToolStripMenuItem.Name = "optionsToolStripMenuItem";
            this.optionsToolStripMenuItem.Size = new System.Drawing.Size(116, 22);
            this.optionsToolStripMenuItem.Text = "&Options";
            // 
            // helpToolStripMenuItem
            // 
            this.helpToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.aboutToolStripMenuItem});
            this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
            this.helpToolStripMenuItem.Size = new System.Drawing.Size(44, 23);
            this.helpToolStripMenuItem.Text = "&Help";
            // 
            // aboutToolStripMenuItem
            // 
            this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
            this.aboutToolStripMenuItem.Size = new System.Drawing.Size(116, 22);
            this.aboutToolStripMenuItem.Text = "&About...";
            // 
            // toolStripMenuItem1
            // 
            this.toolStripMenuItem1.Enabled = false;
            this.toolStripMenuItem1.Name = "toolStripMenuItem1";
            this.toolStripMenuItem1.Size = new System.Drawing.Size(164, 23);
            this.toolStripMenuItem1.Text = "<-- 그냥 메뉴들; 기능 없음";
            // 
            // toolPanel
            // 
            this.toolPanel.Controls.Add(this.selectorRadio);
            this.toolPanel.Controls.Add(this.label1);
            this.toolPanel.Controls.Add(this.startRadio);
            this.toolPanel.Controls.Add(this.goalRadio);
            this.toolPanel.Controls.Add(this.label2);
            this.toolPanel.Controls.Add(this.obstacleRadio);
            this.toolPanel.Controls.Add(this.obstacleInvisibleRadio);
            this.toolPanel.Controls.Add(this.label3);
            this.toolPanel.Controls.Add(this.attractorRadio);
            this.toolPanel.Controls.Add(this.tracerRadio);
            this.toolPanel.Dock = System.Windows.Forms.DockStyle.Left;
            this.toolPanel.Location = new System.Drawing.Point(0, 27);
            this.toolPanel.Name = "toolPanel";
            this.toolPanel.Size = new System.Drawing.Size(94, 502);
            this.toolPanel.TabIndex = 3;
            // 
            // selectorRadio
            // 
            this.selectorRadio.Appearance = System.Windows.Forms.Appearance.Button;
            this.selectorRadio.Checked = true;
            this.selectorRadio.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.selectorRadio.Image = global::DDDMapEditorPrototype.Properties.Resources.pointer;
            this.selectorRadio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.selectorRadio.Location = new System.Drawing.Point(3, 3);
            this.selectorRadio.Name = "selectorRadio";
            this.selectorRadio.Size = new System.Drawing.Size(85, 35);
            this.selectorRadio.TabIndex = 0;
            this.selectorRadio.TabStop = true;
            this.selectorRadio.Tag = "Selector";
            this.selectorRadio.Text = "Selector";
            this.selectorRadio.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.selectorRadio.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.selectorRadio.Click += new System.EventHandler(this.toolSelector_Click);
            // 
            // label1
            // 
            this.label1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label1.Location = new System.Drawing.Point(3, 44);
            this.label1.Margin = new System.Windows.Forms.Padding(3);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(85, 2);
            this.label1.TabIndex = 1;
            // 
            // startRadio
            // 
            this.startRadio.Appearance = System.Windows.Forms.Appearance.Button;
            this.startRadio.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.startRadio.Image = global::DDDMapEditorPrototype.Properties.Resources.start;
            this.startRadio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.startRadio.Location = new System.Drawing.Point(3, 52);
            this.startRadio.Name = "startRadio";
            this.startRadio.Size = new System.Drawing.Size(85, 35);
            this.startRadio.TabIndex = 0;
            this.startRadio.Tag = "Start";
            this.startRadio.Text = "Start";
            this.startRadio.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.startRadio.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.startRadio.Click += new System.EventHandler(this.toolSelector_Click);
            // 
            // goalRadio
            // 
            this.goalRadio.Appearance = System.Windows.Forms.Appearance.Button;
            this.goalRadio.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.goalRadio.Image = global::DDDMapEditorPrototype.Properties.Resources.goal;
            this.goalRadio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.goalRadio.Location = new System.Drawing.Point(3, 93);
            this.goalRadio.Name = "goalRadio";
            this.goalRadio.Size = new System.Drawing.Size(85, 35);
            this.goalRadio.TabIndex = 0;
            this.goalRadio.Tag = "Goal";
            this.goalRadio.Text = "Goal";
            this.goalRadio.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.goalRadio.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.goalRadio.Click += new System.EventHandler(this.toolSelector_Click);
            // 
            // label2
            // 
            this.label2.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label2.Location = new System.Drawing.Point(3, 134);
            this.label2.Margin = new System.Windows.Forms.Padding(3);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(85, 2);
            this.label2.TabIndex = 1;
            // 
            // obstacleRadio
            // 
            this.obstacleRadio.Appearance = System.Windows.Forms.Appearance.Button;
            this.obstacleRadio.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.obstacleRadio.Image = global::DDDMapEditorPrototype.Properties.Resources.obstacle;
            this.obstacleRadio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.obstacleRadio.Location = new System.Drawing.Point(3, 142);
            this.obstacleRadio.Name = "obstacleRadio";
            this.obstacleRadio.Size = new System.Drawing.Size(85, 35);
            this.obstacleRadio.TabIndex = 0;
            this.obstacleRadio.Tag = "Obstacle";
            this.obstacleRadio.Text = "Obstacle";
            this.obstacleRadio.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.obstacleRadio.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.obstacleRadio.Click += new System.EventHandler(this.toolSelector_Click);
            // 
            // obstacleInvisibleRadio
            // 
            this.obstacleInvisibleRadio.Appearance = System.Windows.Forms.Appearance.Button;
            this.obstacleInvisibleRadio.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.obstacleInvisibleRadio.Image = global::DDDMapEditorPrototype.Properties.Resources.invisible_obstacle;
            this.obstacleInvisibleRadio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.obstacleInvisibleRadio.Location = new System.Drawing.Point(3, 183);
            this.obstacleInvisibleRadio.Name = "obstacleInvisibleRadio";
            this.obstacleInvisibleRadio.Size = new System.Drawing.Size(85, 35);
            this.obstacleInvisibleRadio.TabIndex = 0;
            this.obstacleInvisibleRadio.Tag = "InvisibleObstacle";
            this.obstacleInvisibleRadio.Text = "Obstacle";
            this.obstacleInvisibleRadio.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.obstacleInvisibleRadio.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.obstacleInvisibleRadio.Click += new System.EventHandler(this.toolSelector_Click);
            // 
            // label3
            // 
            this.label3.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label3.Location = new System.Drawing.Point(3, 224);
            this.label3.Margin = new System.Windows.Forms.Padding(3);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(85, 2);
            this.label3.TabIndex = 1;
            // 
            // attractorRadio
            // 
            this.attractorRadio.Appearance = System.Windows.Forms.Appearance.Button;
            this.attractorRadio.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.attractorRadio.Image = global::DDDMapEditorPrototype.Properties.Resources.attractor;
            this.attractorRadio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.attractorRadio.Location = new System.Drawing.Point(3, 232);
            this.attractorRadio.Name = "attractorRadio";
            this.attractorRadio.Size = new System.Drawing.Size(85, 35);
            this.attractorRadio.TabIndex = 0;
            this.attractorRadio.Tag = "Attractor";
            this.attractorRadio.Text = "Attractor";
            this.attractorRadio.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.attractorRadio.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.attractorRadio.Click += new System.EventHandler(this.toolSelector_Click);
            // 
            // tracerRadio
            // 
            this.tracerRadio.Appearance = System.Windows.Forms.Appearance.Button;
            this.tracerRadio.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.tracerRadio.Image = global::DDDMapEditorPrototype.Properties.Resources.tracer;
            this.tracerRadio.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.tracerRadio.Location = new System.Drawing.Point(3, 273);
            this.tracerRadio.Name = "tracerRadio";
            this.tracerRadio.Size = new System.Drawing.Size(85, 35);
            this.tracerRadio.TabIndex = 0;
            this.tracerRadio.Tag = "Tracer";
            this.tracerRadio.Text = "Tracer";
            this.tracerRadio.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.tracerRadio.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.tracerRadio.Click += new System.EventHandler(this.toolSelector_Click);
            // 
            // viewPanel
            // 
            this.viewPanel.AutoScroll = true;
            this.viewPanel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.viewPanel.Controls.Add(this.stageView);
            this.viewPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.viewPanel.Location = new System.Drawing.Point(94, 27);
            this.viewPanel.Name = "viewPanel";
            this.viewPanel.Size = new System.Drawing.Size(641, 502);
            this.viewPanel.TabIndex = 5;
            // 
            // stageView
            // 
            this.stageView.BackColor = System.Drawing.Color.Black;
            this.stageView.Font = new System.Drawing.Font("Verdana", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.stageView.Location = new System.Drawing.Point(5, 3);
            this.stageView.Name = "stageView";
            this.stageView.Size = new System.Drawing.Size(765, 801);
            this.stageView.TabIndex = 0;
            this.stageView.Paint += new System.Windows.Forms.PaintEventHandler(this.stageView_Paint);
            this.stageView.MouseMove += new System.Windows.Forms.MouseEventHandler(this.stageView_MouseMove);
            this.stageView.MouseDown += new System.Windows.Forms.MouseEventHandler(this.stageView_MouseDown);
            this.stageView.MouseUp += new System.Windows.Forms.MouseEventHandler(this.stageView_MouseUp);
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.coordLabel});
            this.statusStrip1.Location = new System.Drawing.Point(0, 529);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(735, 22);
            this.statusStrip1.TabIndex = 6;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // coordLabel
            // 
            this.coordLabel.Name = "coordLabel";
            this.coordLabel.Size = new System.Drawing.Size(0, 17);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(735, 551);
            this.Controls.Add(this.viewPanel);
            this.Controls.Add(this.toolPanel);
            this.Controls.Add(this.menuStrip1);
            this.Controls.Add(this.statusStrip1);
            this.Name = "Form1";
            this.Text = "DDD StageEditor";
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.toolPanel.ResumeLayout(false);
            this.viewPanel.ResumeLayout(false);
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveAsToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem editToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem undoToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem redoToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator3;
        private System.Windows.Forms.ToolStripMenuItem cutToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem copyToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem pasteToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator4;
        private System.Windows.Forms.ToolStripMenuItem selectAllToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem toolsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem optionsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem helpToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem aboutToolStripMenuItem;
        private System.Windows.Forms.FlowLayoutPanel toolPanel;
        private System.Windows.Forms.RadioButton selectorRadio;
        private System.Windows.Forms.RadioButton obstacleRadio;
        private System.Windows.Forms.RadioButton obstacleInvisibleRadio;
        private System.Windows.Forms.ToolStripTextBox toolStripMenuItem1;
        private System.Windows.Forms.Panel viewPanel;
        private System.Windows.Forms.RadioButton attractorRadio;
        private System.Windows.Forms.RadioButton goalRadio;
        private System.Windows.Forms.RadioButton startRadio;
        private System.Windows.Forms.RadioButton tracerRadio;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private BufferedPanel stageView;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel coordLabel;


    }
}

